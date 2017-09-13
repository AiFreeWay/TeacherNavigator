package com.teachernavigator.teachernavigator.presentation.screens.auth.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.text.TextUtils
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IAuthInteractor
import com.teachernavigator.teachernavigator.domain.models.Monade
import com.teachernavigator.teachernavigator.domain.models.SingUpData
import com.teachernavigator.teachernavigator.presentation.screens.auth.activities.abstractions.AuthParentView
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.abstractions.RegistrationView
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.abstractions.IRegistrationPresenter
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import javax.inject.Inject

/**
 * Created by root on 24.08.17.
 */
class FmtRegistrationPresenter : BasePresenter<RegistrationView>(), IRegistrationPresenter {

    @Inject
    lateinit var mAuthInteractor: IAuthInteractor

    init {
        Logger.logDebug("created PRESENTER FmtRegistrationPresenter")
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
        mView!!.showToast(getContext().getString(R.string.registration_error))
    }

    override fun singUp() {
        val singUpData = mView!!.getSingUpData()
        if (checkSingUpData(singUpData))
            addDissposable(mAuthInteractor.singUp(singUpData)
                    .doOnSubscribe { startProgress() }
                    .subscribe(this::doOnSingUp, this::doOnError))
    }

    private fun doOnSingUp(result: Monade) {
        stopProgress()
        if (!result.isError)
            mView!!.showAccountCreatedDilog()
    }

    private fun checkSingUpData(singUpData: SingUpData): Boolean {
        if (TextUtils.isEmpty(singUpData.email)) {
            mView!!.showToast(getContext().getString(R.string.indicate_email))
            return false
        }
        if (TextUtils.isEmpty(singUpData.password)) {
            mView!!.showToast(getContext().getString(R.string.indicate_password))
            return false
        }
        if (TextUtils.isEmpty(singUpData.full_name)) {
            mView!!.showToast(getContext().getString(R.string.indicate_full_name))
            return false
        }
        if (TextUtils.isEmpty(singUpData.birthday)) {
            mView!!.showToast(getContext().getString(R.string.indicate_birthday))
            return false
        }
        if (TextUtils.isEmpty(singUpData.work_or_learn_place)) {
            mView!!.showToast(getContext().getString(R.string.indicate_work_or_learn_place))
            return false
        }
        if (TextUtils.isEmpty(singUpData.experience)) {
            mView!!.showToast(getContext().getString(R.string.indicate_experience))
            return false
        }
        if (singUpData.unionist && TextUtils.isEmpty(singUpData.number_of_union_ticket)) {
            mView!!.showToast(getContext().getString(R.string.indicate_number_of_unionist_ticket))
            return false
        }
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