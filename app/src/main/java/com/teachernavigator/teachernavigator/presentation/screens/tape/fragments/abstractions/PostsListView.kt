package com.teachernavigator.teachernavigator.presentation.screens.tape.fragments.abstractions

import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.screens.common.ChildView

/**
 * Created by root on 18.08.17.
 */
interface PostsListView : ChildView {

    fun loadPosts(posts: List<Post>)
    fun showNoDataText()
    fun hideNoDataText()
    fun refresh()
}