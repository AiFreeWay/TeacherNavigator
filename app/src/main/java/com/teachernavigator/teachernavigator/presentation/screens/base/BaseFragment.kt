package com.teachernavigator.teachernavigator.presentation.screens.base

import android.arch.lifecycle.LifecycleRegistry
import android.support.v4.app.Fragment

/**
 * Created by root on 14.08.17.
 */
abstract class BaseFragment : Fragment(), ChildView {

    private val mLifecycle: LifecycleRegistry = LifecycleRegistry(this)

    override fun getParentView(): ParentView = activity as ParentView

    override fun getLifecycle(): LifecycleRegistry = mLifecycle
}