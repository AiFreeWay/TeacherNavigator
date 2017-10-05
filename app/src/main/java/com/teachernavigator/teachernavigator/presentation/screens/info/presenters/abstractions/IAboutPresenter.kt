package com.teachernavigator.teachernavigator.presentation.screens.info.presenters.abstractions

import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.info.fragments.abstractions.AboutView

/**
 * Created by lliepmah on 05.10.17
 */
interface IAboutPresenter : ViewAttacher<AboutView> {
    fun openVk() : Unit?
    fun openFb(): Unit?
    fun openInstagram(): Unit?
}