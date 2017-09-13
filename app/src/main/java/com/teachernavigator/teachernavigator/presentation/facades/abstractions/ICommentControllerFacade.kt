package com.teachernavigator.teachernavigator.presentation.facades.abstractions

import com.teachernavigator.teachernavigator.domain.models.Comment

/**
 * Created by root on 13.09.17.
 */
interface ICommentControllerFacade {

    fun openBranch(comment: Comment, callbak: ICommentControllerFacadeCallback)
    fun subscribe(comment: Comment, callbak: ICommentControllerFacadeCallback)
    fun openProfileScreen(comment: Comment, callbak: ICommentControllerFacadeCallback)
}