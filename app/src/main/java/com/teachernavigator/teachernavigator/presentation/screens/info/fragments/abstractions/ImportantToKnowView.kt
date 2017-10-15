package com.teachernavigator.teachernavigator.presentation.screens.info.fragments.abstractions

import com.teachernavigator.teachernavigator.presentation.models.Info
import com.teachernavigator.teachernavigator.presentation.models.PostModel
import com.teachernavigator.teachernavigator.presentation.screens.common.ChildView

interface ImportantToKnowView : ChildView, PostActionsView {

    fun setThemes(infoThemes: List<Info>)
    fun setInfoPosts(posts: List<PostModel>)

    fun  showRefresh()
    fun  hideRefresh()
}