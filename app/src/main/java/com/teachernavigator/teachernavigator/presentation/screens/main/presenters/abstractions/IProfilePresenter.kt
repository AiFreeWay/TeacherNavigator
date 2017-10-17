package com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions

import android.widget.ImageView
import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.main.activities.abstractions.ProfileView
import java.io.File

/**
 * Created by root on 18.09.17
 */
interface IProfilePresenter : ViewAttacher<ProfileView> {

    fun navigateBack()
    fun exit()
    fun initialLoad(isMyProfile: Boolean, userId: Int)
    fun refresh()
    fun subscribe()

    fun uploadPhoto(imageView: ImageView, file: File)
}