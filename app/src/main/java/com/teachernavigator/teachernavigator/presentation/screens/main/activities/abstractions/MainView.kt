package com.teachernavigator.teachernavigator.presentation.screens.main.activities.abstractions

import com.teachernavigator.teachernavigator.presentation.screens.common.ParentView


/**
 * Created by root on 14.08.17
 */
interface MainView : ParentView {

    fun openSideMenu()
    fun closeSideMenu()
    fun updateProfile()
}