package com.teachernavigator.teachernavigator.presentation.ui.main.activities.abstractions

import com.teachernavigator.teachernavigator.presentation.ui.base.ParentView


/**
 * Created by root on 14.08.17.
 */
interface MainView : ParentView {

    fun startProgress()
    fun stopProgress()
    fun openSideMenu()
    fun closeSideMenu()
    fun setToolbarTitle(title: String)
}