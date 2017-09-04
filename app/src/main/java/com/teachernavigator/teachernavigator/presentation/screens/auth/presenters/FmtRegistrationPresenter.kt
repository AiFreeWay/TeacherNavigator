package com.teachernavigator.teachernavigator.presentation.screens.auth.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IAuthInteractor
import com.teachernavigator.teachernavigator.domain.models.Monade
import com.teachernavigator.teachernavigator.domain.models.SingUpData
import com.teachernavigator.teachernavigator.presentation.screens.auth.activities.abstractions.AuthParentView
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.abstractions.RegistrationView
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.abstractions.IRegistrationPresenter
import com.teachernavigator.teachernavigator.presentation.screens.base.BasePresenter
import javax.inject.Inject

/**
 * Created by root on 24.08.17.
 */
class FmtRegistrationPresenter : BasePresenter<RegistrationView>(), IRegistrationPresenter {

    @Inject
    lateinit var mAuthInteractor: IAuthInteractor

    init {
        if (BuildConfig.DEBUG) Logger.logDebug("created PRESENTER FmtRegistrationPresenter")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        mView!!.getParentView().setToolbarTitle(R.string.registration)
        (mView!!.getParentView() as AuthParentView).showActionBar()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun attachView(view: RegistrationView) {
        super.attachView(view)
        inject()
    }

    override fun doOnError(error: Throwable) {
        super.doOnError(error)
        stopProgress()
    }

    override fun singUp() {
        val singUpData = mView!!.getSingUpData()
        if (checkSingUpData(singUpData))
            addDissposable(mAuthInteractor.singUp(singUpData)
                    .doOnSubscribe { startProgress() }
                    .subscribe(this::doOnSingUp, this::doOnError))
    }

    private fun doOnSingUp(monade: Monade) {
        stopProgress()
    }

    private fun checkSingUpData(singUpData: SingUpData): Boolean {
        return true
    }

    private fun inject() {
        mView!!.getParentView()
                .getParentScreenComponent()
                .inject(this)
    }

    private fun startProgress() {
        mView!!.getParentView().startProgress()
        mView!!.lockUi()
    }

    private fun stopProgress() {
        mView!!.getParentView().stopProgress()
        mView!!.unlockUi()
    }
}