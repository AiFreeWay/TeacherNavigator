package com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.abstractions

import android.content.Intent
import android.support.v4.app.Fragment
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.abstractions.AuthView
import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher

/**
 * Created by root on 22.08.17
 */
interface IAuthPresenter : ViewAttacher<AuthView> {

    fun singInViaVkontakte() : Unit?
    fun singInViaFacebook(fmt: Fragment)
    fun singInViaTwitter()
    fun singInViaGooglePlus()
    fun singIn(login: String, password: String)
    fun openSingUpScreen()
    fun openRestorePasswordScreen()

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}