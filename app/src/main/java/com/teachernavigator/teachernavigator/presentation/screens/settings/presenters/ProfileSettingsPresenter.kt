package com.teachernavigator.teachernavigator.presentation.screens.settings.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.widget.Toast
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.settings.fragments.abstractions.ProfileSettingsView
import com.teachernavigator.teachernavigator.presentation.screens.settings.presenters.abstractions.IProfileSettingsPresenter

/**
 * Created by root on 18.09.17
 */
class ProfileSettingsPresenter : BasePresenter<ProfileSettingsView>(), IProfileSettingsPresenter {

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun doOnError(error: Throwable) {
        super.doOnError(error)
        mView?.getParentView()?.stopProgress()
        mView?.unlockUi()
        Toast.makeText(mView!!.getContext(), mView!!.getContext().getString(R.string.error_throwed), Toast.LENGTH_SHORT).show()
    }

    private fun doOnSubscribe() {
        mView?.getParentView()?.startProgress()
        mView?.lockUi()
    }

}