package com.teachernavigator.teachernavigator.presentation.screens.auth.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.models.SingUpData
import com.teachernavigator.teachernavigator.presentation.dialogs.AccountCreatedDialog
import com.teachernavigator.teachernavigator.presentation.screens.auth.activities.abstractions.AuthParentView
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.abstractions.RegistrationView
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.FmtRegistrationPresenter
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.abstractions.IRegistrationPresenter
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment

/**
 * Created by root on 24.08.17.
 */
class RegistrationFragment : BaseFragment(), RegistrationView {

    companion object {
        val FRAGMENT_KEY = "registration_fragment"
    }

    @BindView(R.id.fmt_registration_et_full_name) lateinit var mEtFullName: EditText
    @BindView(R.id.fmt_registration_et_birthday) lateinit var mEtBirthday: EditText
    @BindView(R.id.fmt_registration_et_work_place) lateinit var mEtWorkPlace: EditText
    @BindView(R.id.fmt_registration_et_position) lateinit var mEtPosition: EditText
    @BindView(R.id.fmt_registration_et_experience) lateinit var mEtExperience: EditText
    @BindView(R.id.fmt_registration_chb_trade_unionist_yes) lateinit var mChbTradeUnionistYes: CheckBox
    @BindView(R.id.fmt_registration_chb_trade_unionist_no) lateinit var mChbTradeUnionistNo: CheckBox
    @BindView(R.id.fmt_registration_et_trade_union_ticket_number) lateinit var mEtTradeUnionTicketNumber: EditText
    @BindView(R.id.fmt_registration_et_email) lateinit var mEtEmail: EditText
    @BindView(R.id.fmt_registration_et_phone) lateinit var mEtPhone: EditText
    @BindView(R.id.fmt_registration_et_password) lateinit var mEtPassword: EditText
    @BindView(R.id.fmt_registration_chb_agreement) lateinit var mChbAgreement: CheckBox
    @BindView(R.id.fmt_registration_btn_sing_up) lateinit var mBtnSingUp: Button

    private val mPresenter: IRegistrationPresenter = FmtRegistrationPresenter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fmt_registration, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPresenter.attachView(this)
        mBtnSingUp.setOnClickListener { mPresenter.singUp() }

        mChbTradeUnionistYes.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked)
                mEtTradeUnionTicketNumber.visibility = View.VISIBLE
            else
                mEtTradeUnionTicketNumber.visibility = View.GONE

            mChbTradeUnionistNo.isChecked = !isChecked
        }

        mChbTradeUnionistNo.setOnCheckedChangeListener { compoundButton, isChecked ->
            mChbTradeUnionistYes.isChecked = !isChecked
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
        mEtFullName.isEnabled = enabled
        mEtBirthday.isEnabled = enabled
        mEtWorkPlace.isEnabled = enabled
        mEtPosition.isEnabled = enabled
        mEtExperience.isEnabled = enabled
        mChbTradeUnionistYes.isEnabled = enabled
        mChbTradeUnionistNo.isEnabled = enabled
        mEtTradeUnionTicketNumber.isEnabled = enabled
        mEtEmail.isEnabled = enabled
        mEtPhone.isEnabled = enabled
        mEtPassword.isEnabled = enabled
        mChbAgreement.isEnabled = enabled
        mBtnSingUp.isEnabled = enabled
    }

    override fun getSingUpData(): SingUpData {
        return SingUpData(mEtEmail.text.toString(),
                mEtPassword.text.toString(),
                mEtFullName.text.toString(),
                mEtBirthday.text.toString(),
                mEtWorkPlace.text.toString(),
                "Not indicated",
                mEtPosition.text.toString(),
                mEtExperience.text.toString(),
                isTradeUnionist(),
                mEtTradeUnionTicketNumber.text.toString(),
                mEtPhone.text.toString())
    }

    override fun showAccountCreatedDilog() {
        val dialogFragment = AccountCreatedDialog.newInstance{ (getParentView() as AuthParentView).navigateBack() }
        dialogFragment.show(fragmentManager, AccountCreatedDialog.FRAGMENT_MANAGER_TAG)
    }

    private fun isTradeUnionist(): Boolean = mChbTradeUnionistYes.isChecked
}