package com.teachernavigator.teachernavigator.presentation.screens.base

/**
 * Created by root on 18.08.17.
 */
interface ViewAttacher<V> {

    fun attachView(view: V)
    fun detachView()
}