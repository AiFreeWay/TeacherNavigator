package com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions

import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacade
import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.SavedPostsView

/**
 * Created by root on 08.09.17.
 */
interface ISavedPostsPresenter : ViewAttacher<SavedPostsView> {

    fun getPostControllerFacade(): IPostControllerFacade
    fun getPosts()
    fun openPostSearchScreen()
    fun refresh()
}