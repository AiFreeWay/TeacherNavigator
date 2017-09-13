package com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.abstractions

import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacade
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacadeCallback
import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.tape.activities.absctraction.PostCommentsView

/**
 * Created by root on 08.09.17.
 */
interface IPostCommentsPresenter : ViewAttacher<PostCommentsView> {

    fun navigateBack()
    fun getIPostControllerFacade(): IPostControllerFacade
    fun doComment(post: Post, text: String)
}