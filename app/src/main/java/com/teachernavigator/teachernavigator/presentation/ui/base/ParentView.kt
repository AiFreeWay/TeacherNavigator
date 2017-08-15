package com.teachernavigator.teachernavigator.presentation.ui.base

import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.teachernavigator.teachernavigator.presentation.ui.base.BaseView

/**
 * Created by root on 14.08.17.
 */
interface ParentView : BaseView {

    fun getActivity(): AppCompatActivity
    fun getSupportFragmentManager(): FragmentManager
    fun getFragmentLayoutId(): Int
}