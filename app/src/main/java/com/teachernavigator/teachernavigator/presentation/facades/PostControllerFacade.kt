package com.teachernavigator.teachernavigator.presentation.facades

import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.domain.controllers.IPostController
import com.teachernavigator.teachernavigator.domain.models.Monade
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacade
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacadeCallback
import com.teachernavigator.teachernavigator.presentation.screens.auth.activities.AuthActivity
import com.teachernavigator.teachernavigator.presentation.screens.common.ParentView
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

    override fun like(vote: Boolean, post: Post, callbak: IPostControllerFacadeCallback) {
        mPostController.like(vote, post, { doOnUserNotAuth() })
                .subscribe({ onSubscribe(it,  { if (vote) callbak.onLike() else callbak.onDislike() }) }, { doOnError(it, callbak) })
    }

    override fun save(post: Post, callbak: IPostControllerFacadeCallback) {
        mPostController.save(post, { doOnUserNotAuth() })
                .subscribe({ onSubscribe(it,  { callbak.onSave() }) }, { doOnError(it, callbak) })
    }

    override fun subscribe(post: Post, callbak: IPostControllerFacadeCallback) {
        mPostController.subscribe(post, { doOnUserNotAuth() })
                .subscribe({ onSubscribe(it,  { callbak.onSubscribe() }) }, { doOnError(it, callbak) })
    }

    override fun complain(post: Post, callbak: IPostControllerFacadeCallback) {
        mPostController.complain(post, { doOnUserNotAuth() })
                .subscribe({ onSubscribe(it,  { callbak.onComplain() }) }, { doOnError(it, callbak) })
    }

    override fun openCommentsScreen(post: Post, callbak: IPostControllerFacadeCallback) {
        mPostController.openCommentsScreen(post, mParentView.getActivity(), { doOnUserNotAuth() })
                .subscribe({}, { doOnError(it, callbak) })
    }

    override fun openProfileScreen(post: Post, callbak: IPostControllerFacadeCallback) {
        mPostController.openProfileScreen(post, mParentView.getActivity(), { doOnUserNotAuth() })
                .subscribe({}, { doOnError(it, callbak) })
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

    private fun onSubscribe(monade: Monade, onSubscribe: () -> Unit) {
        try {
            if (!monade.isError)
                onSubscribe.invoke()
        } catch (e: Exception) {
            // -> ^.^ <-
        }
    }
}