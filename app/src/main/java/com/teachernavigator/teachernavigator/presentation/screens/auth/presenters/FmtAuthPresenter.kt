package com.teachernavigator.teachernavigator.presentation.screens.auth.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.screens.auth.activities.abstractions.AuthParentView
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.RegistrationFragment
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.RestorePasswordFragment
import com.teachernavigator.teachernavigator.presentation.screens.base.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.abstractions.AuthView
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.abstractions.IAuthPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by root on 22.08.17.
 */
class FmtAuthPresenter : BasePresenter<AuthView>(), IAuthPresenter {

    @Inject
    lateinit var mRouter: Router

    init {
        if (BuildConfig.DEBUG) Logger.logDebug("created PRESENTER FmtAuthPresenter")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        mView!!.getParentView().setToolbarTitle(R.string.auth)
        (mView!!.getParentView() as AuthParentView).disableHomeToolbarButton()
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
        mView!!.getParentView().stopProgress()
    }

    override fun singInViaVkontakte() {
    }

    override fun singInViaFacebook() {}

    override fun singInViaTwitter() {}

    override fun singInViaGooglePlus() {}

    override fun singIn(login: String, password: String) {}

    override fun openSingUpScreen() {
        mRouter.navigateTo(RegistrationFragment.FRAGMENT_KEY)
    }

    override fun openRestorePasswordScreen() {
        mRouter.navigateTo(RestorePasswordFragment.FRAGMENT_KEY)
    }

    private fun inject() {
        mView!!.getParentView()
                .getParentScreenComponent()
                .inject(this)
    }
}