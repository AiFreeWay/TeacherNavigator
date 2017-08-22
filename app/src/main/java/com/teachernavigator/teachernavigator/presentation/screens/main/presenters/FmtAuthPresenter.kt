package com.teachernavigator.teachernavigator.presentation.screens.main.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.screens.base.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.AuthView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IAuthPresenter

/**
 * Created by root on 22.08.17.
 */
class FmtAuthPresenter : BasePresenter<AuthView>(), IAuthPresenter {

    init {
        if (BuildConfig.DEBUG) Logger.logDebug("created PRESENTER FmtAuthPresenter")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        mView!!.getMainView().setToolbarTitle(R.string.auth)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun attachView(view: AuthView) {
        super.attachView(view)
        inject()
    }

    override fun doOnError(error: Throwable) {
        super.doOnError(error)
        mView!!.getMainView().stopProgress()
    }

    private fun inject() {
        mView!!.getMainView()
                .getParentScreenComponent()
                .inject(this)
    }
}