package com.teachernavigator.teachernavigator.presentation.screens.settings.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.widget.Toast
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.settings.fragments.abstractions.ProfileSettingsView
import com.teachernavigator.teachernavigator.presentation.screens.settings.presenters.abstractions.IProfileSettingsPresenter

/**
 * Created by root on 18.09.17.
 */
class FmtProfileSettingsPresenter : BasePresenter<ProfileSettingsView>(), IProfileSettingsPresenter {

    init {
        Logger.logDebug("created PRESENTER FmtAppSettingsPresenter")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun attachView(view: ProfileSettingsView) {
        super.attachView(view)
        inject()
    }

    override fun doOnError(error: Throwable) {
        super.doOnError(error)
        mView!!.getParentView().stopProgress()
        mView!!.unlockUi()
        Toast.makeText(mView!!.getContext(), mView!!.getContext().getString(R.string.error_throwed), Toast.LENGTH_SHORT).show()
    }

    private fun doOnSubscribe() {
        mView!!.getParentView().startProgress()
        mView!!.lockUi()
    }

    private fun inject() {
        mView!!.getParentView()
                .getParentScreenComponent()
                .inject(this)
    }
}