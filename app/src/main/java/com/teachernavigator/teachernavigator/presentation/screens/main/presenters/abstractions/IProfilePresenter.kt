package com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions

import android.widget.ImageView
import com.teachernavigator.teachernavigator.application.di.components.ParentScreenComponent
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacade
import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.main.activities.abstractions.ProfileView
import java.io.File

/**
 * Created by root on 18.09.17.
 */
interface IProfilePresenter : ViewAttacher<ProfileView> {

    fun getProfile()
    fun getProfile(userId: Int)
    fun getParentScreenComponent(): ParentScreenComponent
    fun navigateBack()
    fun getPostControllerFacade(): IPostControllerFacade
    fun exit()
    fun uploadPhoto(imageView: ImageView, file: File)
}