package com.teachernavigator.teachernavigator.presentation.menu.abstractions

import android.view.ViewGroup
import com.teachernavigator.teachernavigator.presentation.menu.binders.BaseMenuBinder

/**
 * Created by root on 28.08.17.
 */
interface BindStrategy {

    fun createBinder(parent: ViewGroup, type: Int): BaseMenuBinder
}