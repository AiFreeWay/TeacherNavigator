package com.teachernavigator.teachernavigator.presentation.screens.settings.fragments

import android.os.Bundle
import android.support.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.settings.fragments.abstractions.ProfileSettingsView
import com.teachernavigator.teachernavigator.presentation.screens.settings.presenters.abstractions.IProfileSettingsPresenter
import kotlinx.android.synthetic.main.fmt_profile_settings.*
import javax.inject.Inject

/**
 * Created by root on 18.09.17
 */
class ProfileSettingsFragment : BaseFragment(), ProfileSettingsView {

    companion object {
        val MASK_PHONE = "+7 ([000]) [000] [00] [00]"
    }

    @Inject
    lateinit var presenter: IProfileSettingsPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater?.inflate(R.layout.fmt_profile_settings, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mParentScreenComponent.inject(this)
        presenter.attachView(this)
        presenter.loadSettings()

        fmtProfileSettingsTvBirthday.setOnClickListener { presenter.pickDate() }
        fmtProfileSettingsBtnSingUp.setOnClickListener { save() }
        fmtProfileSettingsTvExperience.setOnClickListener { presenter.pickExperience() }

        fmtProfileSettingsRgTradeUnionist.setOnCheckedChangeListener { _, chId ->
            TransitionManager.beginDelayedTransition(fmtProfileSettingsEtTradeUnionTicketNumber.parent as ViewGroup)
            if (chId == R.id.fmtProfileSettingsChbTradeUnionistYes) {
                fmtProfileSettingsEtTradeUnionTicketNumber.visibility = View.VISIBLE
            } else {
                fmtProfileSettingsEtTradeUnionTicketNumber.visibility = View.GONE
            }
        }

        updateMask()
    }

    private fun updateMask() {
        MaskedTextChangedListener(MASK_PHONE, false, fmtProfileSettingsEtPhone, null, null).let {
            fmtProfileSettingsEtPhone.addTextChangedListener(it)
            fmtProfileSettingsEtPhone.onFocusChangeListener = it
        }
    }

    private fun save() {
        presenter.save(
                fullName = fmtProfileSettingsEtFullName.text.toString(),
                workOrLearnPlace = fmtProfileSettingsEtWorkPlace.text.toString(),
                position = fmtProfileSettingsEtPosition.text.toString(),
                district = fmtProfileSettingsEtDistrict.text.toString(),
                unionist = fmtProfileSettingsChbTradeUnionistYes.isChecked,
                numberOfUnionTicket = fmtProfileSettingsEtTradeUnionTicketNumber.text.toString(),
                email = fmtProfileSettingsEtEmail.text.toString(),
                phoneNumber = fmtProfileSettingsEtPhone.text.toString(),
                password = fmtProfileSettingsEtPassword.text.toString()
        )
    }

    override fun setProfile(fullName: String, workOrLearnPlace: String, position: String, district: String,
                            unionist: Boolean, numberOfUnionTicket: String, email: String, phoneNumber: String) {
        fmtProfileSettingsEtFullName.setText(fullName)
        fmtProfileSettingsEtWorkPlace.setText(workOrLearnPlace)
        fmtProfileSettingsEtPosition.setText(position)
        fmtProfileSettingsEtDistrict.setText(district)

        fmtProfileSettingsRgTradeUnionist.check(if (unionist) {
            fmtProfileSettingsEtTradeUnionTicketNumber.visibility = View.VISIBLE
            R.id.fmtProfileSettingsChbTradeUnionistYes
        } else {
            fmtProfileSettingsEtTradeUnionTicketNumber.visibility = View.GONE
            R.id.fmtProfileSettingsChbTradeUnionistNo
        })

        fmtProfileSettingsEtTradeUnionTicketNumber.setText(numberOfUnionTicket)

        fmtProfileSettingsEtEmail.setText(email)
        fmtProfileSettingsEtPhone.setText(phoneNumber)
        updateMask()

        fmtProfileSettingsEtPassword.setText(R.string.empty)
    }

    override fun setDate(dateString: String) {
        fmtProfileSettingsTvBirthday.text = dateString
    }

    override fun setExperience(experience: Int) {
        fmtProfileSettingsTvExperience.text = resources.getQuantityString(R.plurals.plural_years_exp, experience, experience)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

}