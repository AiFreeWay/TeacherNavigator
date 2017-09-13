package com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.abstractions

import android.os.Bundle
import com.teachernavigator.teachernavigator.application.di.components.ParentScreenComponent
import com.teachernavigator.teachernavigator.presentation.screens.auth.activities.abstractions.AuthParentView
import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher

/**
 * Created by root on 24.08.17.
 */
interface IAuthParentPresenter : ViewAttacher<AuthParentView> {

    fun openStartFragment(savedState: Bundle?)
    fun getParentScreenComponent(): ParentScreenComponent
    fun navigateBack()
}