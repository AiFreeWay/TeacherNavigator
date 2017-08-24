package com.teachernavigator.teachernavigator.presentation.menu.abstractions

import com.teachernavigator.teachernavigator.presentation.models.MenuData
import io.reactivex.Observable
import io.reactivex.Observer

/**
 * Created by root on 24.08.17.
 */
interface IPresenterChannel {

    fun getOutputChannel(): Observer<MenuData<*>>
    fun getInputChannel(): Observable<MenuData<*>>
}