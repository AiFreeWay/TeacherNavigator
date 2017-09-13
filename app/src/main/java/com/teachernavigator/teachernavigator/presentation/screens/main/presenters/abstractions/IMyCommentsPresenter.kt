package com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions

import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacade
import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.MyCommentsView

/**
 * Created by root on 08.09.17.
 */
interface IMyCommentsPresenter : ViewAttacher<MyCommentsView> {

    fun loadComments()
    fun openPost(postId: Int)
    fun getIPostControllerFacade(): IPostControllerFacade
}