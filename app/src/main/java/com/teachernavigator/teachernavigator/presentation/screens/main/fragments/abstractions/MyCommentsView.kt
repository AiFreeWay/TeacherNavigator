package com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions

import com.teachernavigator.teachernavigator.domain.models.Comment
import com.teachernavigator.teachernavigator.presentation.screens.base.ChildView

/**
 * Created by root on 08.09.17.
 */
interface MyCommentsView : ChildView {

    fun loadComments(data: List<Comment>)
    fun showNoDataText()
    fun hideNoDataText()
}