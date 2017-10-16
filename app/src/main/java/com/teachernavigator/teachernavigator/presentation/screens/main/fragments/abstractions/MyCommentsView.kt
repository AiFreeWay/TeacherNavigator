package com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions

import com.teachernavigator.teachernavigator.presentation.models.PostCommentModel
import com.teachernavigator.teachernavigator.presentation.screens.common.ChildView
import com.teachernavigator.teachernavigator.presentation.screens.info.fragments.abstractions.PostActionsView

/**
 * Created by root on 08.09.17
 */
interface MyCommentsView : ChildView, PostActionsView {

    fun loadComments(data: List<PostCommentModel>)

    fun showNoDataText()
    fun hideNoDataText()

    fun showRefresh()
    fun hideRefresh()
}