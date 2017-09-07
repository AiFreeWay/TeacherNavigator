package com.teachernavigator.teachernavigator.presentation.facades

import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.domain.controllers.IPostController
import com.teachernavigator.teachernavigator.domain.models.Monade
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacade
import com.teachernavigator.teachernavigator.presentation.screens.auth.activities.AuthActivity
import com.teachernavigator.teachernavigator.presentation.screens.base.ParentView
import com.teachernavigator.teachernavigator.presentation.utils.ActivityRouter
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by root on 08.09.17.
 */
class PostControllerFacade @Inject constructor(private val mPostController: IPostController,
                                               private val mParentView: ParentView) : IPostControllerFacade {

    init {
        if (BuildConfig.DEBUG) Logger.logDebug("created Facade PostControllerFacade")
    }

    override fun like(post: Post): Observable<Monade> = mPostController.like(post, { doOnUserNotAuth() })

    override fun dislike(post: Post): Observable<Monade> = mPostController.dislike(post, { doOnUserNotAuth() })

    override fun save(post: Post): Observable<Monade> = mPostController.save(post, { doOnUserNotAuth() })

    override fun subscribe(post: Post): Observable<Monade> = mPostController.subscribe(post, { doOnUserNotAuth() })

    override fun complain(post: Post): Observable<Monade> = mPostController.complain(post, { doOnUserNotAuth() })

    override fun openCommentsScreen(post: Post) {
        mPostController.openCommentsScreen(post, mParentView.getActivity(), { doOnUserNotAuth() })
    }

    override fun openProfileScreen(post: Post) {
        mPostController.openProfileScreen(post, mParentView.getActivity(), { doOnUserNotAuth() })
    }

    override fun openPostDetailScreen(post: Post) {
        mPostController.openPostDetailScreen(post, mParentView.getActivity())
    }

    private fun doOnUserNotAuth() {
        ActivityRouter.openActivity(mParentView.getActivity(), AuthActivity::class.java)
    }
}