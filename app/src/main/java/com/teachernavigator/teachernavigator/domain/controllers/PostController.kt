package com.teachernavigator.teachernavigator.domain.controllers

import android.app.Activity
import android.os.Bundle
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IAuthInteractor
import com.teachernavigator.teachernavigator.domain.models.Monade
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.screens.tape.activities.PostDetailActivity
import com.teachernavigator.teachernavigator.presentation.utils.ActivityRouter
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by root on 07.09.17.
 */
class PostController @Inject constructor(private val mAuthInteractor: IAuthInteractor) : IPostController {

    init {
        if (BuildConfig.DEBUG) Logger.logDebug("created CONTROLLER PostController")
    }

    override fun like(post: Post, doOnUserNotAuth: () -> Unit): Observable<Monade> =
        mAuthInteractor.isAuth().map { mapAction(it, doOnUserNotAuth) }

    override fun dislike(post: Post, doOnUserNotAuth: () -> Unit): Observable<Monade> =
            mAuthInteractor.isAuth().map { mapAction(it, doOnUserNotAuth) }

    override fun save(post: Post, doOnUserNotAuth: () -> Unit): Observable<Monade> =
            mAuthInteractor.isAuth().map { mapAction(it, doOnUserNotAuth) }

    override fun subscribe(post: Post, doOnUserNotAuth: () -> Unit): Observable<Monade> =
            mAuthInteractor.isAuth().map { mapAction(it, doOnUserNotAuth) }

    override fun complain(post: Post, doOnUserNotAuth: () -> Unit): Observable<Monade> =
            mAuthInteractor.isAuth().map { mapAction(it, doOnUserNotAuth) }

    override fun openCommentsScreen(post: Post, activity: Activity, doOnUserNotAuth: () -> Unit) {
        mAuthInteractor.isAuth().map { mapOpenScreenAction(it, activity, doOnUserNotAuth) }
                .subscribe({}, { Logger.logError(it) })
    }

    override fun openProfileScreen(post: Post, activity: Activity, doOnUserNotAuth: () -> Unit) {
        mAuthInteractor.isAuth().map { mapOpenScreenAction(it, activity, doOnUserNotAuth) }
                .subscribe({}, { Logger.logError(it) })
    }

    override fun openPostDetailScreen(post: Post, activity: Activity) {
        val bundle = Bundle()
        bundle.putInt(PostDetailActivity.POST_ID_KEY, post.id!!)
        bundle.putString(PostDetailActivity.POST_TITLE_KEY, post.title!!)
        ActivityRouter.openActivity(activity, bundle, PostDetailActivity::class.java)
    }

    private fun mapAction(isUserAuth: Boolean, doOnUserNotAuth: () -> Unit): Monade {
        if (isUserAuth)
            return Monade(false)
        else
            doOnUserNotAuth.invoke()
        return Monade(false)
    }

    private fun mapOpenScreenAction(isUserAuth: Boolean, activity: Activity, doOnUserNotAuth: () -> Unit) {
        if (isUserAuth)
            ActivityRouter.openActivity(activity, PostDetailActivity::class.java)
        else
            doOnUserNotAuth.invoke()
    }
}