package com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions

import com.teachernavigator.teachernavigator.presentation.models.PostModel
import com.teachernavigator.teachernavigator.presentation.screens.common.ChildView

/**
 * Created by root on 13.09.17.
 */
interface MyPublicationsView : ChildView {

    fun loadMyPublications(posts: List<PostModel>)
    fun showNoDataText()
    fun hideNoDataText()
}