package com.teachernavigator.teachernavigator.presentation.screens.settings.fragments.abstractions

import com.teachernavigator.teachernavigator.presentation.screens.common.ChildView

/**
 * Created by root on 18.09.17
 */
interface ProfileSettingsView : ChildView {

    fun setDate(dateString: String)
    fun setExperience(experience: Int)
    fun setProfile(fullName: String, workOrLearnPlace: String, position: String, district: String, unionist: Boolean, numberOfUnionTicket: String, email: String, phoneNumber: String)

}