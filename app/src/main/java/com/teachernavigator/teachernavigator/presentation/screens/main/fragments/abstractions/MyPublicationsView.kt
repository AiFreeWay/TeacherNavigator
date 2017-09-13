package com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions

import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.screens.common.ChildView

/**
 * Created by root on 13.09.17.
 */
interface MyPublicationsView : ChildView {

    fun loadMyPublications(posts: List<Post>)
    fun showNoDataText()
    fun hideNoDataText()
}