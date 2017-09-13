package com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.abstractions

import com.teachernavigator.teachernavigator.presentation.screens.common.ChildView

/**
 * Created by root on 28.08.17.
 */
interface RestorePasswordView : ChildView {

    fun lockUi()
    fun unlockUi()
}