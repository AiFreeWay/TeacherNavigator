package com.teachernavigator.teachernavigator.presentation.screens.auth.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IAuthInteractor
import com.teachernavigator.teachernavigator.domain.models.Monade
import com.teachernavigator.teachernavigator.presentation.screens.auth.activities.abstractions.AuthParentView
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.abstractions.RestorePasswordView
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.abstractions.IRestorePasswordPresenter
import com.teachernavigator.teachernavigator.presentation.screens.base.BasePresenter
import javax.inject.Inject

/**
 * Created by root on 28.08.17.
 */
class FmtRestorePasswordPresenter : BasePresenter<RestorePasswordView>(), IRestorePasswordPresenter {

    @Inject
    lateinit var mAuthInteractor: IAuthInteractor

    init {
        Logger.logDebug("created PRESENTER FmtRestorePasswordPresenter")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        mView!!.getParentView().setToolbarTitle(R.string.auth)
        (mView!!.getParentView() as AuthParentView).hightActionBar()
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
        stopProgress()
        mView!!.showToast(getContext().getString(R.string.restore_password_error))
    }

    override fun restorePassword(login: String) {
        addDissposable(mAuthInteractor.restorePassword(login)
                .doOnSubscribe { startProgress() }
                .subscribe(this::doOnSingRestore, this::doOnError))
    }

    private fun doOnSingRestore(result: Monade) {
        stopProgress()
        if (!result.isError)
            mView!!.showToast(getContext().getString(R.string.account_created_text))
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