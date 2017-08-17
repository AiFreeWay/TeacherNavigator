package com.teachernavigator.teachernavigator.presentation.screens.base

import com.teachernavigator.teachernavigator.presentation.screens.main.activities.abstractions.MainView

/**
 * Created by root on 15.08.17.
 */
interface ChildView : BaseView {

    fun getMainView(): MainView
}