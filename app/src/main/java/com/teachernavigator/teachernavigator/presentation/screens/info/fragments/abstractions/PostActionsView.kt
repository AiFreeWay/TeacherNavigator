package com.teachernavigator.teachernavigator.presentation.screens.info.fragments.abstractions

import com.teachernavigator.teachernavigator.presentation.models.PostModel
import com.teachernavigator.teachernavigator.presentation.screens.common.ChildView

/**
 * Created by lliepmah on 08.10.17
 */
interface PostActionsView : ChildView {

    fun showRefresh()
    fun hideRefresh()

    fun updatePost(post: PostModel)
}