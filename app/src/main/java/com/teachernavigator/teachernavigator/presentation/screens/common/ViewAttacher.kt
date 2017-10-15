package com.teachernavigator.teachernavigator.presentation.screens.common

/**
 * Created by root on 18.08.17
 */
interface ViewAttacher<in V> {

    fun attachView(view: V)
    fun detachView()
}