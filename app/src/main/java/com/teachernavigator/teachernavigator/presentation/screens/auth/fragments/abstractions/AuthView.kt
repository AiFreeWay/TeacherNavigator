package com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.abstractions

import com.teachernavigator.teachernavigator.presentation.screens.common.ChildView

/**
 * Created by root on 22.08.17.
 */
interface AuthView : ChildView {

    fun lockUi()
    fun unlockUi()
}