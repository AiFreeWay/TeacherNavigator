package com.teachernavigator.teachernavigator.presentation.screens.common

import android.arch.lifecycle.LifecycleRegistryOwner
import android.content.Context

/**
 * Created by root on 11.08.17
 */
interface BaseView : LifecycleRegistryOwner {

    fun getContext(): Context
}