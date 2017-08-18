package com.teachernavigator.teachernavigator.presentation.screens.main.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.components.DaggerParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.modules.ParentScreenModule
import com.teachernavigator.teachernavigator.presentation.factories.TapeFragmentsFactory
import com.teachernavigator.teachernavigator.presentation.screens.base.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.TapeView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.ITapePresenter

/**
 * Created by root on 17.08.17.
 */
class FmtTapePresenter : BasePresenter<TapeView>(), ITapePresenter {

    init {
        if (BuildConfig.DEBUG) Logger.logDebug("created PRESENTER FmtTapePresenter")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        mView!!.getMainView().setToolbarTitle(R.string.tape)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun attachView(view: TapeView) {
        super.attachView(view)
        inject()
    }

    override fun loadFragments() {
        mView!!.loadOrdersFragments(TapeFragmentsFactory.createItems(mView!!.getMainView().getActivity()))
    }

    private fun inject() {
        DaggerParentScreenComponent.builder()
                .rootComponent(getRootComponent(mView!!.getMainView().getActivity()))
                .parentScreenModule(ParentScreenModule(mView!!.getMainView()))
                .build()
                .inject(this)
    }
}