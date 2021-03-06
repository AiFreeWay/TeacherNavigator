package com.teachernavigator.teachernavigator.presentation.menu.channels

import com.teachernavigator.teachernavigator.presentation.models.MenuData
import io.reactivex.Observable
import io.reactivex.Observer

/**
 * Created by root on 24.08.17.
 */
interface IHolderChannel {

    fun getOutputChannel(): Observer<MenuData<*>>
    fun getInputChannel(): Observable<MenuData<*>>
}