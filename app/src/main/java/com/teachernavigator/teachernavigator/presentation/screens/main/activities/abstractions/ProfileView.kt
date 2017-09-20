package com.teachernavigator.teachernavigator.presentation.screens.main.activities.abstractions

import com.teachernavigator.teachernavigator.presentation.models.ProfilePostConteainer
import com.teachernavigator.teachernavigator.presentation.screens.common.ParentView

/**
 * Created by root on 18.09.17.
 */
interface ProfileView : ParentView {

    fun loadProfile(data: List<ProfilePostConteainer>)
}