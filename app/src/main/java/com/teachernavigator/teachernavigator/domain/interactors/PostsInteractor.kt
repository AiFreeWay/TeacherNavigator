package com.teachernavigator.teachernavigator.domain.interactors

import com.teachernavigator.teachernavigator.data.models.CommentNetwork
import com.teachernavigator.teachernavigator.data.models.FileInfo
import com.teachernavigator.teachernavigator.data.models.PostNetwork
import com.teachernavigator.teachernavigator.data.network.requests.CommentRequest
import com.teachernavigator.teachernavigator.data.repository.abstractions.IPostsRepository
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IPostsInteractor
import com.teachernavigator.teachernavigator.domain.models.PostType
import com.teachernavigator.teachernavigator.domain.models.Tag
import com.teachernavigator.teachernavigator.presentation.models.Info
import com.teachernavigator.teachernavigator.presentation.models.PostsSource
import com.teachernavigator.teachernavigator.presentation.utils.applySchedulers
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by root on 22.08.17
 */
class PostsInteractor @Inject constructor(private val mRepository: IPostsRepository) : IPostsInteractor {

    override fun getPosts(postType: PostType, postsSource: PostsSource) = when (postType to postsSource) {

        PostType.post to PostsSource.Common -> getLatestPosts()
        PostType.post to PostsSource.Best -> getBestPosts()

        PostType.poll to PostsSource.Common -> getPolls()
        PostType.news to PostsSource.Common -> getNewsPosts()

        else -> throw Error("Unknown Type")
    }

    override fun sendPost(title: String, text: String, tags: List<String>, fileInfo: FileInfo?): Single<PostNetwork> =
            mRepository.sendPost(title, text, tags, fileInfo)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())

    override fun getTrends(): Single<List<Tag>> =
            mRepository.getTrends()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())

    override fun getTags(): Single<List<Tag>> =
            mRepository.getTags()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())

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

    override fun getPolls(): Single<List<PostNetwork>> =
            mRepository.getPolls()
                    .map { it.results }
                    .applySchedulers()

    override fun getLatestPosts(): Single<List<PostNetwork>> =
            mRepository.getPosts()
                    .map { it.results }
                    .applySchedulers()

    override fun getBestPosts(): Single<List<PostNetwork>> =
            mRepository.getBestPosts()
                    .map { it.results }
                    .applySchedulers()

    override fun getNewsPosts(): Single<List<PostNetwork>> =
            mRepository.getNewsPosts()
                    .map { it.results }
                    .applySchedulers()

    override fun getSavedPosts(): Single<List<PostNetwork>> =
            mRepository.getSavedPosts()
                    .map { it.results }
                    .applySchedulers()

    override fun getMyPublications(): Single<List<PostNetwork>> =
            mRepository.getMyPublications()
                    .map { it.toList() }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())

    override fun getUserPost(userId: Int): Single<List<PostNetwork>> =
            mRepository.getUserPost(userId)
                    .map { it.toList() }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())

}