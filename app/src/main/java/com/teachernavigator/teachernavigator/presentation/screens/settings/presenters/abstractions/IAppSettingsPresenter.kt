package com.teachernavigator.teachernavigator.presentation.screens.settings.presenters.abstractions

import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.settings.fragments.abstractions.AppSettingsView

/**
 * Created by root on 18.09.17.
 */
interface IAppSettingsPresenter : ViewAttacher<AppSettingsView> {

    fun getSettings()
    fun changeNightTheme(state: Boolean)
    fun changePush(state: Boolean)
    fun changeSound(state: Boolean)
}