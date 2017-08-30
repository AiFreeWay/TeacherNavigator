package com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.abstractions

import com.teachernavigator.teachernavigator.domain.models.SingUpData
import com.teachernavigator.teachernavigator.presentation.screens.base.ChildView

/**
 * Created by root on 24.08.17.
 */
interface RegistrationView : ChildView {

    fun lockUi()
    fun unlockUi()
    fun getSingUpData(): SingUpData
}