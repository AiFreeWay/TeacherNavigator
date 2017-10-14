package com.teachernavigator.teachernavigator.presentation.screens.auth.fragments

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.components.DaggerParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.components.ParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.modules.ParentScreenModule
import com.teachernavigator.teachernavigator.application.utils.rootComponent
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.abstractions.AuthView
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.abstractions.IAuthPresenter
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import kotlinx.android.synthetic.main.fmt_auth.*
import javax.inject.Inject


/**
 * Created by root on 22.08.17
 */

class AuthFragment : BaseFragment(), AuthView {

    companion object {
        val FRAGMENT_KEY = "auth_fragment"
    }

    private lateinit var mParentScreenComponent: ParentScreenComponent

    @Inject
    lateinit var presenter: IAuthPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater?.inflate(R.layout.fmt_auth, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        inject()
        presenter.attachView(this)

        fmtAuthIvVk.setOnClickListener { presenter.singInViaVkontakte() }
        fmtAuthIvFb.setOnClickListener { presenter.singInViaFacebook(this) }
        fmtAuthIvTw.setOnClickListener { presenter.singInViaTwitter() }
        fmtAuthIvGp.setOnClickListener { presenter.singInViaGooglePlus() }
        fmtAuthTvRestorePassword.setOnClickListener { presenter.openRestorePasswordScreen() }
        fmtAuthBtnSingIn.setOnClickListener {
            if (checkLoginFields()) {
                val login = fmtAuthEtLogin.text.toString()
                val pasword = fmtAuthEtPassword.text.toString()
                presenter.singIn(login, pasword)
            }
        }

        fmtAuthTvSingUp.setOnClickListener { presenter.openSingUpScreen() }
    }

    private fun inject() {
        mParentScreenComponent = DaggerParentScreenComponent.builder()
                .rootComponent(rootComponent())
                .parentScreenModule(ParentScreenModule(getParentView()))
                .build()
                .also { it.inject(this) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    override fun lockUi() {
        setEnabledViews(false)
    }

    override fun unlockUi() {
        setEnabledViews(true)
    }

    private fun setEnabledViews(enabled: Boolean) {
        fmtAuthIvVk.isEnabled = enabled
        fmtAuthIvFb.isEnabled = enabled
        fmtAuthIvTw.isEnabled = enabled
        fmtAuthIvGp.isEnabled = enabled

        fmtAuthEtLogin.isEnabled = enabled
        fmtAuthEtPassword.isEnabled = enabled

        fmtAuthTvRestorePassword.isEnabled = enabled
        fmtAuthBtnSingIn.isEnabled = enabled
        fmtAuthTvSingUp.isEnabled = enabled
    }

    private fun checkLoginFields(): Boolean {
        if (TextUtils.isEmpty(fmtAuthEtLogin.text.toString())) {
            showToast(getString(R.string.indicate_login))
            return false
        }
        if (TextUtils.isEmpty(fmtAuthEtPassword.text.toString())) {
            showToast(getString(R.string.indicate_password))
            return false
        }
        return true
    }
}
