package com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions

import com.teachernavigator.teachernavigator.presentation.models.PostModel
import com.teachernavigator.teachernavigator.presentation.screens.info.fragments.abstractions.PostActionsView

/**
 * Created by lliepmah on 08.10.17
 */
interface PostCommentsView : PostActionsView {

    fun setPost(post: PostModel)
    fun clearField()
    fun scrollToLast()

    fun  showRefresh()
    fun  hideRefresh()
}