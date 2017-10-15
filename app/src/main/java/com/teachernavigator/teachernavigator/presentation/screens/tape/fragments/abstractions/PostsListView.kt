package com.teachernavigator.teachernavigator.presentation.screens.tape.fragments.abstractions

import com.teachernavigator.teachernavigator.presentation.models.PostModel
import com.teachernavigator.teachernavigator.presentation.screens.common.ChildView
import com.teachernavigator.teachernavigator.presentation.screens.info.fragments.abstractions.PostActionsView

/**
 * Created by root on 18.08.17
 */
interface PostsListView : ChildView, PostActionsView {

    fun setPosts(posts: List<PostModel>)

    fun showNoDataText()
    fun hideNoDataText()

    fun  showRefresh()
    fun  hideRefresh()
}