package com.teachernavigator.teachernavigator.presentation.facades

import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.domain.controllers.IPostController
import com.teachernavigator.teachernavigator.domain.models.Comment
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.ICommentControllerFacade
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.ICommentControllerFacadeCallback
import com.teachernavigator.teachernavigator.presentation.screens.auth.activities.AuthActivity
import com.teachernavigator.teachernavigator.presentation.screens.common.ParentView
import com.teachernavigator.teachernavigator.presentation.utils.ActivityRouter
import javax.inject.Inject

/**
 * Created by root on 13.09.17.
 */
class CommentControllerFacade @Inject constructor(private val mPostController: IPostController,
                                                  private val mParentView: ParentView) : ICommentControllerFacade {

    init {
        Logger.logDebug("created Facade CommentControllerFacade")
    }

    override fun openBranch(comment: Comment, callbak: ICommentControllerFacadeCallback) {
        mPostController.openBranch(comment, mParentView.getActivity())
    }

    override fun subscribe(comment: Comment, callbak: ICommentControllerFacadeCallback) {
        mPostController.subscribe(comment, { doOnUserNotAuth() })
                .subscribe({ callbak.onSubscribe() }, { doOnError(it, callbak) })
    }

    override fun openProfileScreen(comment: Comment, callbak: ICommentControllerFacadeCallback) {
        mPostController.openProfileScreen(comment, mParentView.getActivity(), { doOnUserNotAuth() })
    }

    private fun doOnUserNotAuth() {
        ActivityRouter.openActivity(mParentView.getActivity(), AuthActivity::class.java)
    }

    private fun doOnError(error: Throwable, callbak: ICommentControllerFacadeCallback) {
        try {
            callbak.onError(error)
        } catch (e: Exception) {
            // -> @.@ <-
        } finally {
            Logger.logError(error)
        }
    }
}