package com.teachernavigator.teachernavigator.presentation.screens.settings.presenters.abstractions

import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.settings.fragments.abstractions.ProfileSettingsView

/**
 * Created by root on 18.09.17
 */
interface IProfileSettingsPresenter : ViewAttacher<ProfileSettingsView> {

    fun save(fullName: String, workOrLearnPlace: String, position: String, district: String,
               unionist: Boolean, numberOfUnionTicket: String, email: String, phoneNumber: String,
               password: String)

    fun pickDate(): Unit?
    fun pickExperience(): Unit?


    fun loadSettings()
}