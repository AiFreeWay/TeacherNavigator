package com.teachernavigator.teachernavigator.data.repository

import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.data.network.NetworkController
import com.teachernavigator.teachernavigator.data.repository.abstractions.IMainRepository
import com.teachernavigator.teachernavigator.domain.models.Post
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by root on 22.08.17.
 */
class MainRepository @Inject constructor(private val mNetwokController: NetworkController) : IMainRepository {

    init {
        if (BuildConfig.DEBUG) Logger.logDebug("created REPOSITORY MainRepository")
    }

    override fun isAuth(): Observable<Boolean> = Observable.just(true)

    override fun getBestPosts(): Observable<List<Post>> {
        val posts = ArrayList<Post>()
        posts.add(Post())
        return Observable.just(posts)
    }

    override fun getInterviewsPosts(): Observable<List<Post>> {
        val posts = ArrayList<Post>()
        posts.add(Post())
        posts.add(Post())
        return Observable.just(posts)
    }

    override fun getLastPosts(): Observable<List<Post>> {
        val posts = ArrayList<Post>()
        posts.add(Post())
        posts.add(Post())
        posts.add(Post())
        return Observable.just(posts)
    }

    override fun getNewsPosts(): Observable<List<Post>> {
        val posts = ArrayList<Post>()
        posts.add(Post())
        posts.add(Post())
        posts.add(Post())
        posts.add(Post())
        return Observable.just(posts)
    }
}