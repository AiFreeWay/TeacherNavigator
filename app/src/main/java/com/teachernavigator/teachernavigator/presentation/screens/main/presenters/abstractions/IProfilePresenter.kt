package com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions

import com.teachernavigator.teachernavigator.presentation.models.ProfileModel
import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.ProfileView

/**
 * Created by root on 18.09.17
 */
interface IProfilePresenter : ViewAttacher<ProfileView> {

    fun exit()
    fun initialLoad(isMyProfile: Boolean, userId: Int)
    fun refresh()
    fun subscribe()

    fun uploadPhoto(fileName:String, filePath: String, fileMime: String)

    var profile: ProfileModel?
}