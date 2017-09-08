package com.teachernavigator.teachernavigator.domain.controllers

import android.app.Activity
import android.os.Bundle
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.data.repository.abstractions.IMainRepository
import com.teachernavigator.teachernavigator.domain.mappers.BaseMapper
import com.teachernavigator.teachernavigator.domain.mappers.PostsMapper
import com.teachernavigator.teachernavigator.domain.models.Monade
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.screens.tape.activities.PostCommentsActivity
import com.teachernavigator.teachernavigator.presentation.screens.tape.activities.PostDetailActivity
import com.teachernavigator.teachernavigator.presentation.utils.ActivityRouter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by root on 07.09.17.
 */
class PostController @Inject constructor(private val mRepository: IMainRepository) : IPostController {

    init {
        Logger.logDebug("created CONTROLLER PostController")
    }

    override fun like(post: Post, doOnUserNotAuth: () -> Unit): Observable<Monade> {
        if (mRepository.isAuth())
            return mRepository.vote(PostsMapper.mapPostToVoteRequest(post))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .map { BaseMapper.mapBaseResponse(it) }
        else
            doOnUserNotAuth.invoke()
        return Observable.just(Monade.FAILARY_MONADE)
    }

    override fun dislike(post: Post, doOnUserNotAuth: () -> Unit): Observable<Monade> {
        if (mRepository.isAuth())
            return mRepository.vote(PostsMapper.mapPostToVoteRequest(post))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .map { BaseMapper.mapBaseResponse(it) }
        else
            doOnUserNotAuth.invoke()
        return Observable.just(Monade.FAILARY_MONADE)
    }

    override fun save(post: Post, doOnUserNotAuth: () -> Unit): Observable<Monade> {
        if (mRepository.isAuth())
            return mRepository.savePost(PostsMapper.mapPostToSavePostRequest(post))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .map { BaseMapper.mapBaseResponse(it) }
        else
            doOnUserNotAuth.invoke()
        return Observable.just(Monade.FAILARY_MONADE)
    }

    override fun subscribe(post: Post, doOnUserNotAuth: () -> Unit): Observable<Monade> {
        if (mRepository.isAuth())
            return Observable.just(Monade.SUCCESSFULLY_MONADE)
        else
            doOnUserNotAuth.invoke()
        return Observable.just(Monade.FAILARY_MONADE)
    }

    override fun complain(post: Post, doOnUserNotAuth: () -> Unit): Observable<Monade> {
        if (mRepository.isAuth())
            return Observable.just(Monade.SUCCESSFULLY_MONADE)
        else
            doOnUserNotAuth.invoke()
        return Observable.just(Monade.FAILARY_MONADE)
    }

    override fun openCommentsScreen(post: Post, activity: Activity, doOnUserNotAuth: () -> Unit): Observable<Monade> {
            if (mRepository.isAuth()) {
                val bundle = Bundle()
                bundle.putSerializable(PostCommentsActivity.POST_KEY, post)
                ActivityRouter.openActivity(activity, bundle, PostCommentsActivity::class.java)
            } else
                doOnUserNotAuth.invoke()
        return Observable.just(Monade.FAILARY_MONADE)
    }

    override fun openProfileScreen(post: Post, activity: Activity, doOnUserNotAuth: () -> Unit): Observable<Monade> {
        if (mRepository.isAuth()) {
            val bundle = Bundle()
            bundle.putSerializable(PostCommentsActivity.POST_KEY, post)
            ActivityRouter.openActivity(activity, bundle, PostCommentsActivity::class.java)
        } else
            doOnUserNotAuth.invoke()
        return Observable.just(Monade.FAILARY_MONADE)
    }

    override fun openPostDetailScreen(post: Post, activity: Activity) {
        val bundle = Bundle()
        bundle.putSerializable(PostDetailActivity.POST_KEY, post)
        ActivityRouter.openActivity(activity, bundle, PostDetailActivity::class.java)
    }
}