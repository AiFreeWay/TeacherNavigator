package com.teachernavigator.teachernavigator.presentation.screens.common

import android.arch.lifecycle.LifecycleRegistry
import android.support.v4.app.Fragment
import android.widget.Toast
import com.teachernavigator.teachernavigator.application.di.components.DaggerParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.modules.ParentScreenModule
import com.teachernavigator.teachernavigator.application.utils.rootComponent
import com.teachernavigator.teachernavigator.presentation.models.ToolbarStyle

/**
 * Created by root on 14.08.17
 */

abstract class BaseFragment : Fragment(), ChildView {

    protected val mParentScreenComponent by lazy {
        DaggerParentScreenComponent.builder()
                .rootComponent(rootComponent())
                .parentScreenModule(ParentScreenModule(getParentView()))
                .build()
    }

    override fun onStart() {
        super.onStart()
        getParentView().setToolbarStyle(getToolbarStyle())
        getParentView().updateToolbarAlpha(1F)
    }

    open fun getToolbarStyle() = ToolbarStyle.Over

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