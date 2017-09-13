package com.teachernavigator.teachernavigator.presentation.screens.auth.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.abstractions.RestorePasswordView
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.FmtRestorePasswordPresenter
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.abstractions.IRestorePasswordPresenter
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment

/**
 * Created by root on 28.08.17.
 */
class RestorePasswordFragment : BaseFragment(), RestorePasswordView {

    companion object {
        val FRAGMENT_KEY = "restore_password_fragment"
    }

    @BindView(R.id.fmt_restore_password_et_login) lateinit var mEtLogin: EditText
    @BindView(R.id.fmt_restore_password_btn_restore) lateinit var mBtnRestore: Button

    private val mPresenter: IRestorePasswordPresenter = FmtRestorePasswordPresenter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fmt_restore_password, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPresenter.attachView(this)
        mBtnRestore.setOnClickListener {
            if (checkLoginField())
                mPresenter.restorePassword(mEtLogin.text.toString())
        }
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
        mEtLogin.isEnabled = enabled
        mBtnRestore.isEnabled = enabled
    }

    private fun checkLoginField(): Boolean {
        if (TextUtils.isEmpty(mEtLogin.text.toString())) {
            showToast(getString(R.string.indicate_login))
            return false
        }
        return true
    }
}