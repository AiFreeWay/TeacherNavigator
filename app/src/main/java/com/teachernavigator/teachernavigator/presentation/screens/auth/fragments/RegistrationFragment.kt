package com.teachernavigator.teachernavigator.presentation.screens.auth.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.dialogs.AccountCreatedDialog
import com.teachernavigator.teachernavigator.presentation.screens.auth.activities.abstractions.AuthParentView
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.abstractions.RegistrationView
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.abstractions.IRegistrationPresenter
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import kotlinx.android.synthetic.main.fmt_registration.*
import javax.inject.Inject


/**
 * Created by root on 24.08.17
 */
class RegistrationFragment : BaseFragment(), RegistrationView {

    companion object {
        val FRAGMENT_KEY = "Registration_Fragment _ 312"
    }

    @Inject
    lateinit var presenter: IRegistrationPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fmt_registration, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mParentScreenComponent.inject(this)
        presenter.attachView(this)

        fmtRegistrationTvBirthday.setOnClickListener { presenter.pickDate() }
        fmtRegistrationBtnSingUp.setOnClickListener { signUp() }
        fmtRegistrationTvExperience.setOnClickListener { presenter.pickExperience() }

        fmtRegistrationChbTradeUnionistYes.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked)
                fmtRegistrationEtTradeUnionTicketNumber.visibility = View.VISIBLE
            else
                fmtRegistrationEtTradeUnionTicketNumber.visibility = View.GONE

            fmtRegistrationChbTradeUnionistNo.isChecked = !isChecked
        }

        fmtRegistrationChbTradeUnionistNo.setOnCheckedChangeListener { compoundButton, isChecked ->
            fmtRegistrationChbTradeUnionistYes.isChecked = !isChecked
        }

        val phoneListener = MaskedTextChangedListener("+7 ([000]) [000] [00] [00]", true, fmtRegistrationEtPhone,
                null, null)

        fmtRegistrationEtPhone.addTextChangedListener(phoneListener)
        fmtRegistrationEtPhone.onFocusChangeListener = phoneListener
    }

    private fun signUp() {
        if (!fmtRegistrationChbAgreement.isChecked) {
            showToast(R.string.need_agreement)
        } else {
            presenter.singUp(
                    fullName = fmtRegistrationEtFullName.text.toString(),
                    workOrLearnPlace = fmtRegistrationEtWorkPlace.text.toString(),
                    position = fmtRegistrationEtPosition.text.toString(),
                    district = fmtRegistrationEtDistrict.text.toString(),
                    unionist = isTradeUnionist(),
                    numberOfUnionTicket = fmtRegistrationEtTradeUnionTicketNumber.text.toString(),
                    email = fmtRegistrationEtEmail.text.toString(),
                    phoneNumber = fmtRegistrationEtPhone.text.toString(),
                    password = fmtRegistrationEtPassword.text.toString()
            )
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
        fmtRegistrationEtFullName.isEnabled = enabled
        fmtRegistrationEtWorkPlace.isEnabled = enabled
        fmtRegistrationEtPosition.isEnabled = enabled
        fmtRegistrationEtDistrict.isEnabled = enabled
        fmtRegistrationChbTradeUnionistYes.isEnabled = enabled
        fmtRegistrationChbTradeUnionistNo.isEnabled = enabled
        fmtRegistrationEtTradeUnionTicketNumber.isEnabled = enabled
        fmtRegistrationEtEmail.isEnabled = enabled
        fmtRegistrationEtPhone.isEnabled = enabled
        fmtRegistrationEtPassword.isEnabled = enabled
        fmtRegistrationChbAgreement.isEnabled = enabled
        fmtRegistrationBtnSingUp.isEnabled = enabled
    }


    override fun setDate(dateString: String) {
        fmtRegistrationTvBirthday.text = dateString
    }

    override fun setExperience(experience: Int) {
        fmtRegistrationTvExperience.text = resources.getQuantityString(R.plurals.plural_years, experience, experience)
    }

    override fun showAccountCreatedDialog() {
        val dialogFragment = AccountCreatedDialog.newInstance { (getParentView() as AuthParentView).navigateBack() }
        dialogFragment.show(fragmentManager, AccountCreatedDialog.FRAGMENT_MANAGER_TAG)
    }

    private fun isTradeUnionist(): Boolean = fmtRegistrationChbTradeUnionistYes.isChecked
}