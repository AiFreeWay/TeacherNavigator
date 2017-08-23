package com.teachernavigator.teachernavigator.presentation.screens.main.fragments

import android.os.Bundle
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
import com.teachernavigator.teachernavigator.presentation.screens.base.FragmentChildMainView
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.AuthView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.FmtAuthPresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IAuthPresenter


/**
 * Created by root on 22.08.17.
 */

class AuthFragment : FragmentChildMainView(), AuthView {

    companion object {
        val FRAGMENT_KEY = "auth_fragment"
    }

    @BindView(R.id.fmt_auth_iv_vk)
    lateinit var mIvVk: ImageView
    @BindView(R.id.fmt_auth_iv_fb)
    lateinit var mIvFb: ImageView
    @BindView(R.id.fmt_auth_iv_tw)
    lateinit var mIvTw: ImageView
    @BindView(R.id.fmt_auth_iv_gp)
    lateinit var mIvGp: ImageView

    @BindView(R.id.fmt_auth_et_login)
    lateinit var mEtLogin: EditText
    @BindView(R.id.fmt_auth_et_password)
    lateinit var mEtPassword: EditText

    @BindView(R.id.fmt_auth_tv_restore_password)
    lateinit var mTvRestorePassword: TextView
    @BindView(R.id.fmt_auth_btn_sing_in)
    lateinit var mBtnSingIn: Button
    @BindView(R.id.fmt_auth_btn_sing_up)
    lateinit var mBtnSingUp: Button

    private val mPresenter: IAuthPresenter = FmtAuthPresenter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fmt_auth, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPresenter.attachView(this)

        mIvVk.setOnClickListener { mPresenter::singInViaVkontakte }
        mIvFb.setOnClickListener { mPresenter::singInViaFacebook }
        mIvTw.setOnClickListener { mPresenter::singInViaTwitter }
        mIvGp.setOnClickListener { mPresenter::singInViaGooglePlus }
        mTvRestorePassword.setOnClickListener { mPresenter::restorePassword }
        mBtnSingIn.setOnClickListener {
            val login = mEtLogin.text.toString()
            val pasword = mBtnSingUp.text.toString()
            mPresenter.singIn(login, pasword)
        }
        mBtnSingUp.setOnClickListener { mPresenter::openSingUpScreen }
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
        mIvVk.isClickable = enabled
        mIvFb.isClickable = enabled
        mIvTw.isClickable = enabled
        mIvGp.isClickable = enabled

        mEtLogin.isEnabled = enabled
        mEtPassword.isEnabled = enabled

        mTvRestorePassword.isClickable = enabled
        mBtnSingIn.isClickable = enabled
        mBtnSingUp.isClickable = enabled
    }
}
