package com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions

import com.teachernavigator.teachernavigator.domain.models.Profile
import com.teachernavigator.teachernavigator.presentation.screens.common.ChildView

/**
 * Created by root on 18.09.17.
 */
interface ProfileView : ChildView {

    fun loadProfile(profile: Profile)
}