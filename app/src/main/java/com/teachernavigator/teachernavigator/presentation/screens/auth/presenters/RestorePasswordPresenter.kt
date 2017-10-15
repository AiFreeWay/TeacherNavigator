package com.teachernavigator.teachernavigator.presentation.screens.auth.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IAuthInteractor
import com.teachernavigator.teachernavigator.domain.models.Monade
import com.teachernavigator.teachernavigator.presentation.screens.auth.activities.abstractions.AuthParentView
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.abstractions.RestorePasswordView
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.abstractions.IRestorePasswordPresenter
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.validators.EmailValidator
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by root on 28.08.17
 */
@PerParentScreen
class RestorePasswordPresenter
@Inject constructor(private val router: Router,
                    private val authInteractor: IAuthInteractor) : BasePresenter<RestorePasswordView>(), IRestorePasswordPresenter {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        mView?.getParentView()?.setToolbarTitle(R.string.auth)
        (mView?.getParentView() as? AuthParentView)?.hideActionBar()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun doOnError(error: Throwable) {
        super.doOnError(error)
        stopProgress()
        mView?.failedRestore()
    }

    override fun restorePassword(email: String, phone: String, isPhone: Boolean) {
        if (checkLogin(email, phone, isPhone)) {
            addDissposable(authInteractor.restorePassword(email, phone)
                    .doOnSubscribe { startProgress() }
                    .subscribe(this::doOnSingRestore, this::doOnError))
        }
    }

    private fun checkLogin(email: String, phoneNumber: String, isPhone: Boolean): Boolean {


        if (isPhone && !phoneNumber.isBlank() && phoneNumber.length < 18) {
            mView?.showToast(getContext().getString(R.string.input_phone))
            return false
        }

        if (!isPhone && !EmailValidator.getInstance().isValid(email)) {
            mView?.showToast(getContext().getString(R.string.error_enter_valid_email))
            return false
        }

        return true
    }

    private fun doOnSingRestore(result: Monade) {
        stopProgress()
        if (!result.isError)
            mView?.showToast(getContext().getString(R.string.account_created_text))
    }


    private fun startProgress() {
        mView?.getParentView()?.startProgress()
        mView?.lockUi()
    }

    private fun stopProgress() {
        mView?.getParentView()?.stopProgress()
        mView?.unlockUi()
    }
}