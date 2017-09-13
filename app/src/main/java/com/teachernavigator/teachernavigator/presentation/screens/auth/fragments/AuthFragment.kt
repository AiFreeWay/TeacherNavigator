package com.teachernavigator.teachernavigator.presentation.screens.auth.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.abstractions.AuthView
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.FmtAuthPresenter
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.abstractions.IAuthPresenter


/**
 * Created by root on 22.08.17.
 */

class AuthFragment : BaseFragment(), AuthView {

    companion object {
        val FRAGMENT_KEY = "auth_fragment"
    }

    @BindView(R.id.fmt_auth_iv_vk) lateinit var mIvVk: ImageView
    @BindView(R.id.fmt_auth_iv_fb) lateinit var mIvFb: ImageView
    @BindView(R.id.fmt_auth_iv_tw) lateinit var mIvTw: ImageView
    @BindView(R.id.fmt_auth_iv_gp) lateinit var mIvGp: ImageView

    @BindView(R.id.fmt_auth_et_login) lateinit var mEtLogin: EditText
    @BindView(R.id.fmt_auth_et_password) lateinit var mEtPassword: EditText

    @BindView(R.id.fmt_auth_tv_restore_password) lateinit var mTvRestorePassword: TextView
    @BindView(R.id.fmt_auth_btn_sing_in) lateinit var mBtnSingIn: Button
    @BindView(R.id.fmt_auth_tv_sing_up) lateinit var mTvSingUp: TextView

    private val mPresenter: IAuthPresenter = FmtAuthPresenter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fmt_auth, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPresenter.attachView(this)
        mIvVk.setOnClickListener { mPresenter.singInViaVkontakte() }
        mIvFb.setOnClickListener { mPresenter.singInViaFacebook() }
        mIvTw.setOnClickListener { mPresenter.singInViaTwitter() }
        mIvGp.setOnClickListener { mPresenter.singInViaGooglePlus() }
        mTvRestorePassword.setOnClickListener { mPresenter.openRestorePasswordScreen() }
        mBtnSingIn.setOnClickListener {
            if (checkLoginFields()) {
                val login = mEtLogin.text.toString()
                val pasword = mEtPassword.text.toString()
                mPresenter.singIn(login, pasword)
            }
        }
        mTvSingUp.setOnClickListener { mPresenter.openSingUpScreen() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.detachView()
    }

    override fun lockUi() {
        setEnabledViews(false)
    }

    override fun unlockUi() {
        setEnabledViews(true)
    }
    
    private fun setEnabledViews(enabled: Boolean) {
        mIvVk.isEnabled = enabled
        mIvFb.isEnabled = enabled
        mIvTw.isEnabled = enabled
        mIvGp.isEnabled = enabled

        mEtLogin.isEnabled = enabled
        mEtPassword.isEnabled = enabled

        mTvRestorePassword.isEnabled = enabled
        mBtnSingIn.isEnabled = enabled
        mTvSingUp.isEnabled = enabled
    }

    private fun checkLoginFields(): Boolean {
        if (TextUtils.isEmpty(mEtLogin.text.toString())) {
            showToast(getString(R.string.indicate_login))
            return false
        }
        if (TextUtils.isEmpty(mEtPassword.text.toString())) {
            showToast(getString(R.string.indicate_password))
            return false
        }
        return true
    }
}
