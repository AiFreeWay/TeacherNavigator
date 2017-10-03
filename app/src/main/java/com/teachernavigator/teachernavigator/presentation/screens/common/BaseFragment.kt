package com.teachernavigator.teachernavigator.presentation.screens.common

import android.arch.lifecycle.LifecycleRegistry
import android.support.v4.app.Fragment
import android.widget.Toast

/**
 * Created by root on 14.08.17.
 */
abstract class BaseFragment : Fragment(), ChildView {

    private val mLifecycle by lazy { LifecycleRegistry(this) }

    override fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun showToast(textRes: Int) {
        Toast.makeText(context, textRes, Toast.LENGTH_SHORT).show()
    }

    override fun getParentView(): ParentView = activity as ParentView

    override fun getLifecycle(): LifecycleRegistry = mLifecycle
}