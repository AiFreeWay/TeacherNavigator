package com.teachernavigator.teachernavigator.presentation.screens.base

import android.arch.lifecycle.LifecycleRegistry
import android.support.v4.app.Fragment
import com.teachernavigator.teachernavigator.presentation.screens.main.activities.abstractions.MainView

/**
 * Created by root on 14.08.17.
 */
open class FragmentChildMainView : Fragment(), ChildView {

    private val mLifecycle: LifecycleRegistry = LifecycleRegistry(this)

    override fun getMainView(): MainView = activity as MainView

    override fun getLifecycle(): LifecycleRegistry = mLifecycle
}