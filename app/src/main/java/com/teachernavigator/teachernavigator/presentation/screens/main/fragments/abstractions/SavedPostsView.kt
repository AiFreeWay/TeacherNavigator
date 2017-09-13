package com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions

import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.screens.common.ChildView

/**
 * Created by root on 08.09.17.
 */
interface SavedPostsView : ChildView {

    fun loadPosts(posts: List<Post>)
    fun showNoDataText()
    fun hideNoDataText()
}