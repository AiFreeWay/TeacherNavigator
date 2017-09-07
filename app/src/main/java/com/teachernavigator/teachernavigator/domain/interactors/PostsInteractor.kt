package com.teachernavigator.teachernavigator.domain.interactors

import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.data.models.PostNetwork
import com.teachernavigator.teachernavigator.data.repository.abstractions.ITapeRepository
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IPostsInteractor
import com.teachernavigator.teachernavigator.domain.mappers.PostsMapper
import com.teachernavigator.teachernavigator.domain.models.Post
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by root on 22.08.17.
 */
class PostsInteractor @Inject constructor(private val mRepository: ITapeRepository) : IPostsInteractor {

    init {
        if (BuildConfig.DEBUG) Logger.logDebug("created INTERACTOR PostsInteractor")
    }

    override fun getBestPosts(): Observable<List<Post>> = configePostsObservable(mRepository.getBestPosts())

    override fun getInterviewsPosts(): Observable<List<Post>> = configePostsObservable(mRepository.getInterviewsPosts())

    override fun getLatestPosts(): Observable<List<Post>> = configePostsObservable(mRepository.getLatestPosts())

    override fun getNewsPosts(): Observable<List<Post>> = configePostsObservable(mRepository.getNewsPosts())

    override fun getPostById(postId: Int): Observable<Post> =
            mRepository.getPostById(postId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .map { PostsMapper.mapPost(it) }

    private fun configePostsObservable(observable: Observable<Array<PostNetwork>>) : Observable<List<Post>> =
            observable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .map { PostsMapper.mapPosts(it) }

}