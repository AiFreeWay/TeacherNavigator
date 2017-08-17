package com.teachernavigator.teachernavigator.presentation.screens.main.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.components.DaggerParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.modules.ParentScreenModule
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.TapeView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IFmtTapePresenter

/**
 * Created by root on 17.08.17.
 */
class FmtTapePresenter : IFmtTapePresenter() {

    init {
        if (BuildConfig.DEBUG) Logger.logDebug("created PRESENTER FmtTapePresenter")
    }

    override fun attachView(view: TapeView) {
        super.attachView(view)
        inject()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    private fun inject() {
        DaggerParentScreenComponent.builder()
                .rootComponent(getRootComponent(mView.getMainView().getActivity()))
                .parentScreenModule(ParentScreenModule(mView.getMainView()))
                .build()
                .inject(this)
    }
}