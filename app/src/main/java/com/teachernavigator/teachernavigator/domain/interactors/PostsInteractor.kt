package com.teachernavigator.teachernavigator.domain.interactors

import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.data.models.CommentNetwork
import com.teachernavigator.teachernavigator.data.models.PostNetwork
import com.teachernavigator.teachernavigator.data.network.requests.CommentRequest
import com.teachernavigator.teachernavigator.data.network.responses.PostsResponse
import com.teachernavigator.teachernavigator.data.repository.abstractions.ITapeRepository
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IPostsInteractor
import com.teachernavigator.teachernavigator.domain.mappers.PostsMapper
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.domain.models.PostType
import com.teachernavigator.teachernavigator.presentation.models.Info
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by root on 22.08.17.
 */
class PostsInteractor @Inject constructor(private val mRepository: ITapeRepository) : IPostsInteractor {

    init {
        Logger.logDebug("created INTERACTOR PostsInteractor")
    }

    override fun getPost(postId: Int, postType: PostType): Single<PostNetwork> =
            mRepository.getPost(postId, postType)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())

    override fun sendComment(postId: Int, postType: PostType, text: String): Single<CommentNetwork> =
            mRepository.comment(CommentRequest.build(postId, postType, text))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())

    override fun getInfoPosts(currentTheme: Info): Single<List<PostNetwork>> =
            mRepository.getInfoPosts(currentTheme)
                    .map { it.results }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())

    override fun getBestPosts(): Observable<List<Post>> =
            configePostsObservable(mRepository.getPosts())
                    .map { sortAsBestPosts(it) }

    override fun getInterviewsPosts(): Observable<List<Post>> =
            configePostsObservable(mRepository.getInterviewsPosts())

    override fun getLatestPosts(): Observable<List<Post>> =
            configePostsObservable(mRepository.getPosts())

    override fun getNewsPosts(): Observable<List<Post>> =
            configePostsObservable(mRepository.getNewsPosts())

    override fun getPostById(postId: Int): Observable<Post> =
            mRepository.getPostById(postId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .map { PostsMapper.mapPost(it) }

    override fun getSavedPosts(): Observable<List<Post>> =
            configePostsObservable(mRepository.getSavedPosts())

    override fun getMyPublications(): Observable<List<Post>> =
            configeArrayPostsObservable(mRepository.getMyPublications())

    override fun getUserPost(userId: Int): Observable<List<Post>> =
            configeArrayPostsObservable(mRepository.getUserPost(userId))

    private fun configePostsObservable(observable: Observable<PostsResponse>): Observable<List<Post>> =
            observable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .map { PostsMapper.mapPosts(it) }

    private fun configeArrayPostsObservable(observable: Observable<Array<PostNetwork>>): Observable<List<Post>> =
            observable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .map { PostsMapper.mapPostsFromArray(it) }

    private fun sortAsBestPosts(posts: List<Post>): List<Post> = posts.sortedBy { it.countDislikes!! - it.countLikes!! }
}