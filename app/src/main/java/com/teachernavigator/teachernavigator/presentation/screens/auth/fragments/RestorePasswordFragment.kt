package com.teachernavigator.teachernavigator.presentation.screens.auth.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.abstractions.RestorePasswordView
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.abstractions.IRestorePasswordPresenter
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import kotlinx.android.synthetic.main.fmt_restore_password.*
import javax.inject.Inject

/**
 * Created by root on 28.08.17
 */
class RestorePasswordFragment : BaseFragment(), RestorePasswordView {

    companion object {
        val FRAGMENT_KEY = "restore_password_fragment"
    }

    @Inject
    lateinit var presenter: IRestorePasswordPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater?.inflate(R.layout.fmt_restore_password, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mParentScreenComponent.inject(this)
        presenter.attachView(this)

        fmtRestorePasswordBtnRestore.setOnClickListener {
            presenter.restorePassword(fmtRestorePasswordEtEmail.text.toString(),
                    fmtRestorePasswordEtPhone.text.toString(), fmtRestorePasswordChPhone.isChecked)
        }

        fmtRestorePasswordChSwitch.setOnCheckedChangeListener { _, selectedId ->
            when (selectedId) {
                R.id.fmtRestorePasswordChEmail -> showEmailField()
                R.id.fmtRestorePasswordChPhone -> showPhoneField()
            }
        }

        val phoneListener = MaskedTextChangedListener("+7 ([000]) [000] [00] [00]", true, fmtRestorePasswordEtPhone,
                null, null)
        fmtRestorePasswordEtPhone.addTextChangedListener(phoneListener)
        fmtRestorePasswordEtPhone.onFocusChangeListener = phoneListener
    }

    private fun showEmailField() {
        fmtRestorePasswordEtPhone.visibility = View.GONE
        fmtRestorePasswordEtEmail.visibility = View.VISIBLE
    }

    private fun showPhoneField() {
        fmtRestorePasswordEtPhone.visibility = View.VISIBLE
        fmtRestorePasswordEtEmail.visibility = View.GONE
    }

    override fun failedRestore() {
        if (fmtRestorePasswordChEmail.isChecked) {
            showToast(R.string.email_not_registered)
        } else {
            showToast(R.string.phone_not_registered)
        }
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
        fmtRestorePasswordEtEmail.isEnabled = enabled
        fmtRestorePasswordEtPhone.isEnabled = enabled
        fmtRestorePasswordBtnRestore.isEnabled = enabled
    }
}