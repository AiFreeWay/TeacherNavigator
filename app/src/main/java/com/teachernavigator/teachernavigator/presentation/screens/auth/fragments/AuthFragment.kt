package com.teachernavigator.teachernavigator.presentation.screens.auth.fragments

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import com.teachernavigator.teachernavigator.R
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

    @Inject
    lateinit var presenter: IAuthPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater?.inflate(R.layout.fmt_auth, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mParentScreenComponent.inject(this)
        presenter.attachView(this)

        fmtAuthIvVk.setOnClickListener { presenter.singInViaVkontakte() }
//        fmtAuthIvVk.setOnClickListener { notImplemented() }
        fmtAuthIvFb.setOnClickListener { presenter.singInViaFacebook(this) }
        fmtAuthIvTw.setOnClickListener { presenter.singInViaTwitter() }
//        fmtAuthIvTw.setOnClickListener { notImplemented() }
        fmtAuthIvGp.setOnClickListener { presenter.singInViaGooglePlus() }
//        fmtAuthIvGp.setOnClickListener { notImplemented() }
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
