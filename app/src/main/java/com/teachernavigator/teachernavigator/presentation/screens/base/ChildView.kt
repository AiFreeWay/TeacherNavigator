package com.teachernavigator.teachernavigator.presentation.screens.base

/**
 * Created by root on 15.08.17.
 */
interface ChildView : BaseView {

    fun getParentView(): ParentView
    fun showToast(text: String)
    fun showToast(textRes: Int)
}