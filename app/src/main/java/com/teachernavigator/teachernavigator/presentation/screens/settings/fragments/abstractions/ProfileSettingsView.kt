package com.teachernavigator.teachernavigator.presentation.screens.settings.fragments.abstractions

import com.teachernavigator.teachernavigator.presentation.screens.common.ChildView

/**
 * Created by root on 18.09.17.
 */
interface ProfileSettingsView : ChildView {

    fun lockUi()
    fun unlockUi()
}