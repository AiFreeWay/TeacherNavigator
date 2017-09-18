package com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions

import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.SettingsView

/**
 * Created by root on 18.09.17.
 */
interface ISettingsPresenter : ViewAttacher<SettingsView> {

    fun getSettings()
}