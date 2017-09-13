package com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.abstractions

import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacade
import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.tape.activities.absctraction.PostDetailView

/**
 * Created by root on 07.09.17.
 */
interface IPostDetailPresenter : ViewAttacher<PostDetailView> {

    fun navigateBack()
    fun putPostId(postId: Int)
    fun loadData()
    fun getPostControllerFacade(): IPostControllerFacade
}