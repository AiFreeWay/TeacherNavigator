package com.teachernavigator.teachernavigator.presentation.ui.main.activities.abstractions

import com.teachernavigator.teachernavigator.presentation.ui.base.TopFragmentContainerView


/**
 * Created by root on 14.08.17.
 */
interface MainView : TopFragmentContainerView {

    fun startProgress()
    fun stopProgress()
    fun openSideMenu()
    fun closeSideMenu()
    fun setToolbarTitle(title: String)
}