package com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions

import com.teachernavigator.teachernavigator.presentation.models.PostModel
import com.teachernavigator.teachernavigator.presentation.models.ProfileModel
import com.teachernavigator.teachernavigator.presentation.models.ProfilePostConteainer
import com.teachernavigator.teachernavigator.presentation.screens.common.ChildView
import com.teachernavigator.teachernavigator.presentation.screens.common.ParentView
import com.teachernavigator.teachernavigator.presentation.screens.info.fragments.abstractions.PostActionsView

/**
 * Created by root on 18.09.17
 */
interface ProfileView : ChildView, PostActionsView {

    fun setProfile(data: Pair<ProfileModel, List<PostModel>>)

    fun startProgress()
    fun stopProgress()
    fun updateProfile(profile: ProfileModel)
}