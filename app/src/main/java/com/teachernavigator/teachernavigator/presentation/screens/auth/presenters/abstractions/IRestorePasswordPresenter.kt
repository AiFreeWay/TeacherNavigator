package com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.abstractions

import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.abstractions.RestorePasswordView
import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher

/**
 * Created by root on 28.08.17.
 */
interface IRestorePasswordPresenter : ViewAttacher<RestorePasswordView> {

    fun restorePassword(login: String)
}