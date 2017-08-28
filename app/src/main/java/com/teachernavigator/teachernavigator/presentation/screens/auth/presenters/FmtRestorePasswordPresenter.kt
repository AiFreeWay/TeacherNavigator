package com.teachernavigator.teachernavigator.presentation.screens.auth.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.screens.auth.activities.abstractions.AuthParentView
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.abstractions.RestorePasswordView
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.abstractions.IRestorePasswordPresenter
import com.teachernavigator.teachernavigator.presentation.screens.base.BasePresenter

/**
 * Created by root on 28.08.17.
 */
class FmtRestorePasswordPresenter : BasePresenter<RestorePasswordView>(), IRestorePasswordPresenter {

    init {
        if (BuildConfig.DEBUG) Logger.logDebug("created PRESENTER FmtRestorePasswordPresenter")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        mView!!.getParentView().setToolbarTitle(R.string.auth)
        (mView!!.getParentView() as AuthParentView).enableHomeToolbarButton()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun attachView(view: RestorePasswordView) {
        super.attachView(view)
        inject()
    }

    override fun doOnError(error: Throwable) {
        super.doOnError(error)
        mView!!.getParentView().stopProgress()
    }

    private fun inject() {
        mView!!.getParentView()
                .getParentScreenComponent()
                .inject(this)
    }
}