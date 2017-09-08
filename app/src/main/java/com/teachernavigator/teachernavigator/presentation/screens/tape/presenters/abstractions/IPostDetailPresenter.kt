package com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.abstractions

import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacade
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacadeCallback
import com.teachernavigator.teachernavigator.presentation.screens.base.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.tape.activities.absctraction.PostDetailView

/**
 * Created by root on 07.09.17.
 */
interface IPostDetailPresenter : ViewAttacher<PostDetailView>, IPostControllerFacadeCallback {

    fun navigateBack()
    fun getIPostControllerFacade(): IPostControllerFacade
}