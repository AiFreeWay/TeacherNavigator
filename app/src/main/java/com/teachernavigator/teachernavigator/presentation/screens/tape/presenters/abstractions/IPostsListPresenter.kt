package com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.abstractions

import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacade
import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.tape.fragments.abstractions.PostsListView

/**
 * Created by root on 18.08.17.
 */
interface IPostsListPresenter : ViewAttacher<PostsListView> {

    fun setTapeType(tapeType: Int)
    fun getPostControllerFacade(): IPostControllerFacade
    fun getPosts()
}