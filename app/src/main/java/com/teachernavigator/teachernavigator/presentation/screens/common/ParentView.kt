package com.teachernavigator.teachernavigator.presentation.screens.common

import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.teachernavigator.teachernavigator.application.di.components.ParentScreenComponent

/**
 * Created by root on 14.08.17.
 */
interface ParentView : BaseView {

    fun startProgress()
    fun stopProgress()
    fun setToolbarTitle(title: String)
    fun setToolbarTitle(title: Int)
    fun getParentScreenComponent(): ParentScreenComponent
    fun getActivity(): AppCompatActivity
    fun getSupportFragmentManager(): FragmentManager
    fun getFragmentLayoutId(): Int
}