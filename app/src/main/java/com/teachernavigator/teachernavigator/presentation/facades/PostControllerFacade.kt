package com.teachernavigator.teachernavigator.presentation.facades

import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.domain.controllers.IPostController
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacade
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacadeCallback
import com.teachernavigator.teachernavigator.presentation.screens.auth.activities.AuthActivity
import com.teachernavigator.teachernavigator.presentation.screens.base.ParentView
import com.teachernavigator.teachernavigator.presentation.utils.ActivityRouter
import javax.inject.Inject

/**
 * Created by root on 08.09.17.
 */
class PostControllerFacade @Inject constructor(private val mPostController: IPostController,
                                               private val mParentView: ParentView) : IPostControllerFacade {

    init {
        Logger.logDebug("created Facade PostControllerFacade")
    }

    override fun like(post: Post, callbak: IPostControllerFacadeCallback) {
        mPostController.like(post, { doOnUserNotAuth() })
                .subscribe({ callbak.onLike(it) }, { this::doOnError })
    }

    override fun dislike(post: Post, callbak: IPostControllerFacadeCallback) {
        mPostController.dislike(post, { doOnUserNotAuth() })
                .subscribe({ callbak.onDislike(it) }, { this::doOnError })
    }

    override fun save(post: Post, callbak: IPostControllerFacadeCallback) {
        mPostController.save(post, { doOnUserNotAuth() })
                .subscribe({ callbak.onSave(it) }, { this::doOnError })
    }

    override fun subscribe(post: Post, callbak: IPostControllerFacadeCallback) {
        mPostController.subscribe(post, { doOnUserNotAuth() })
                .subscribe({ callbak.onSubscribe(it) }, { this::doOnError })
    }

    override fun complain(post: Post, callbak: IPostControllerFacadeCallback) {
        mPostController.complain(post, { doOnUserNotAuth() })
                .subscribe({ callbak.onComplain(it) }, { this::doOnError })
    }

    override fun openCommentsScreen(post: Post, callbak: IPostControllerFacadeCallback) {
        if (post.comments != null && post.comments!!.isNotEmpty())
            mPostController.openCommentsScreen(post, mParentView.getActivity(), { doOnUserNotAuth() })
                    .subscribe({}, { this::doOnError })
    }

    override fun openProfileScreen(post: Post, callbak: IPostControllerFacadeCallback) {
        mPostController.openProfileScreen(post, mParentView.getActivity(), { doOnUserNotAuth() })
                .subscribe({}, { this::doOnError })
    }

    override fun openPostDetailScreen(post: Post) {
        mPostController.openPostDetailScreen(post, mParentView.getActivity())
    }

    private fun doOnUserNotAuth() {
        ActivityRouter.openActivity(mParentView.getActivity(), AuthActivity::class.java)
    }

    private fun doOnError(error: Throwable, callbak: IPostControllerFacadeCallback) {
        try {
            callbak.onError(error)
        } catch (e: Exception) {
            // -> @.@ <-
        } finally {
            Logger.logError(error)
        }
    }
}