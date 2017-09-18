package com.teachernavigator.teachernavigator.presentation.screens.settings.fragments.abstractions

import com.teachernavigator.teachernavigator.domain.models.Settings
import com.teachernavigator.teachernavigator.presentation.screens.common.ChildView

/**
 * Created by root on 18.09.17.
 */
interface AppSettingsView : ChildView {

    fun lockUi()
    fun unlockUi()
    fun loadSettings(settings: Settings)
}