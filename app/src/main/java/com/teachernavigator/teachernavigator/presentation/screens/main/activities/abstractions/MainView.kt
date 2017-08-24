package com.teachernavigator.teachernavigator.presentation.screens.main.activities.abstractions

import com.teachernavigator.teachernavigator.application.di.components.ParentScreenComponent
import com.teachernavigator.teachernavigator.presentation.screens.base.ParentView


/**
 * Created by root on 14.08.17.
 */
interface MainView : ParentView {

    fun openSideMenu()
    fun closeSideMenu()
}