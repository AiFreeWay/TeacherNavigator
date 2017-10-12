package com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.abstractions

import com.teachernavigator.teachernavigator.presentation.screens.common.ChildView

/**
 * Created by root on 24.08.17
 */
interface RegistrationView : ChildView {

    fun lockUi()
    fun unlockUi()
    fun showAccountCreatedDialog()

    fun setDate(dateString : String)
    fun setExperience(experience : String)
}