package com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions

import com.teachernavigator.teachernavigator.presentation.screens.base.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.AuthView

/**
 * Created by root on 22.08.17.
 */
interface IAuthPresenter : ViewAttacher<AuthView> {
    
    fun singInViaVkontakte()
    fun singInViaFacebook()
    fun singInViaTwitter()
    fun singInViaGooglePlus()
    fun singIn(login: String, password: String)
    fun openSingUpScreen()
    fun restorePassword()
}