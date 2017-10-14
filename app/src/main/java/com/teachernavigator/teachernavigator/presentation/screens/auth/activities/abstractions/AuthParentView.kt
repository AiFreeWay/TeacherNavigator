package com.teachernavigator.teachernavigator.presentation.screens.auth.activities.abstractions

import com.teachernavigator.teachernavigator.presentation.screens.common.ParentView

/**
 * Created by root on 24.08.17
 */
interface AuthParentView : ParentView {

    fun showActionBar()
    fun hideActionBar()
    fun navigateBack()
}