package com.teachernavigator.teachernavigator.presentation.screens.auth.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IAuthInteractor
import com.teachernavigator.teachernavigator.domain.models.Monade
import com.teachernavigator.teachernavigator.presentation.screens.auth.activities.abstractions.AuthParentView
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.RegistrationFragment
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.RestorePasswordFragment
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.abstractions.AuthView
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.abstractions.IAuthPresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.activities.MainActivity
import com.teachernavigator.teachernavigator.presentation.utils.ActivityRouter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by root on 22.08.17.
 */
class FmtAuthPresenter : BasePresenter<AuthView>(), IAuthPresenter {

    @Inject
    lateinit var mRouter: Router
    @Inject
    lateinit var mAuthInteractor: IAuthInteractor

    init {
        Logger.logDebug("created PRESENTER FmtAuthPresenter")
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

    override fun attachView(view: AuthView) {
        super.attachView(view)
        inject()
    }

    override fun doOnError(error: Throwable) {
        super.doOnError(error)
        stopProgress()
        mView!!.showToast(getContext().getString(R.string.auth_error))
    }

    override fun singInViaVkontakte() {
        mView!!.showToast("Проявите терпение, Алексей, я скоро заработаю!")
        /*addDissposable(mAuthInteractor.singInViaVkontakte()
                .doOnSubscribe { startProgress() }
                .subscribe(this::doOnSingIn, this::doOnError))*/
    }

    override fun singInViaFacebook() {
        mView!!.showToast("Проявите терпение, Алексей, я скоро заработаю!")
        /*addDissposable(mAuthInteractor.singInViaFacebook()
                .doOnSubscribe { startProgress() }
                .subscribe(this::doOnSingIn, this::doOnError))*/
    }

    override fun singInViaTwitter() {
        mView!!.showToast("Проявите терпение, Алексей, я скоро заработаю!")
        /*addDissposable(mAuthInteractor.singInViaTwitter()
                .doOnSubscribe { startProgress() }
                .subscribe(this::doOnSingIn, this::doOnError))*/
    }

    override fun singInViaGooglePlus() {
        mView!!.showToast("Проявите терпение, Алексей, я скоро заработаю!")
        /*addDissposable(mAuthInteractor.singInViaGooglePlus()
                .doOnSubscribe { startProgress() }
                .subscribe(this::doOnSingIn, this::doOnError))*/
    }

    override fun singIn(login: String, password: String) {
        addDissposable(mAuthInteractor.singIn(login, password)
                .doOnSubscribe { startProgress() }
                .subscribe(this::doOnSingIn, this::doOnError))
    }

    override fun openSingUpScreen() {
        mRouter.navigateTo(RegistrationFragment.FRAGMENT_KEY)
    }

    override fun openRestorePasswordScreen() {
        mRouter.navigateTo(RestorePasswordFragment.FRAGMENT_KEY)
    }

    private fun doOnSingIn(result: Monade) {
        stopProgress()
        if (!result.isError)
            ActivityRouter.openActivityAndClosePrevent(mView!!.getParentView().getActivity(), MainActivity::class.java)
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