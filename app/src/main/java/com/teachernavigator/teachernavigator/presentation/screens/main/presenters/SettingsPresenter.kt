package com.teachernavigator.teachernavigator.presentation.screens.main.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.factories.SettingsFragmentFactory
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.SettingsView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.ISettingsPresenter
import javax.inject.Inject

/**
 * Created by root on 18.09.17
 */
class SettingsPresenter : BasePresenter<SettingsView>(), ISettingsPresenter {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        mView!!.getParentView().setToolbarTitle(R.string.settings)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun doOnError(error: Throwable) {
        super.doOnError(error)
        mView!!.getParentView().stopProgress()
    }

    override fun getSettings() {
        val fragmentsContainer = SettingsFragmentFactory.createItems(getContext())
        mView!!.loadOrdersFragments(fragmentsContainer)
    }

}