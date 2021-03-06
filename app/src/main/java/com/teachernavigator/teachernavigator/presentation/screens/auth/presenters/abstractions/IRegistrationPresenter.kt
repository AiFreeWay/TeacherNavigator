package com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.abstractions

import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.abstractions.RegistrationView
import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher

/**
 * Created by root on 24.08.17
 */
interface IRegistrationPresenter : ViewAttacher<RegistrationView> {

    fun singUp(fullName: String, workOrLearnPlace: String, position: String, district: String,
               unionist: Boolean, numberOfUnionTicket: String, email: String, phoneNumber: String,
               password: String)

    fun pickDate(): Unit?
    fun pickExperience(): Unit?
    fun openAgreement()
}