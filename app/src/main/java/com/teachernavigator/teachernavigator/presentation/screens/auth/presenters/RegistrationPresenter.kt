package com.teachernavigator.teachernavigator.presentation.screens.auth.presenters

import android.app.DatePickerDialog
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.text.TextUtils
import com.afollestad.materialdialogs.MaterialDialog
import com.google.gson.Gson
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.data.network.responses.BaseResponse
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IAuthInteractor
import com.teachernavigator.teachernavigator.domain.models.Monade
import com.teachernavigator.teachernavigator.domain.models.SingUpData
import com.teachernavigator.teachernavigator.presentation.models.FieldNames
import com.teachernavigator.teachernavigator.presentation.screens.auth.activities.abstractions.AuthParentView
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.abstractions.RegistrationView
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.abstractions.IRegistrationPresenter
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.AgreementFragment
import com.teachernavigator.teachernavigator.presentation.utils.DialogUtils
import com.teachernavigator.teachernavigator.presentation.utils.formatDisplayDate
import com.teachernavigator.teachernavigator.presentation.utils.formatServerDate
import com.teachernavigator.teachernavigator.presentation.validators.EmailValidator
import retrofit2.HttpException
import ru.terrakok.cicerone.Router
import java.io.InputStreamReader
import java.util.*
import javax.inject.Inject

/**
 * Created by root on 24.08.17
 */
@PerParentScreen
class RegistrationPresenter
@Inject
constructor(private val router: Router,
            private val authInteractor: IAuthInteractor) : BasePresenter<RegistrationView>(), IRegistrationPresenter {

    override fun openAgreement() {
        router.navigateTo(AgreementFragment.FRAGMENT_KEY)
    }

    private var mBirthDate: Date? = Date()
    private var mExperience = 0

    private var mDateDialog: DatePickerDialog? = null
    private var mNumberDialog: MaterialDialog? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        mView!!.getParentView().setToolbarTitle(R.string.registration)
        (mView!!.getParentView() as AuthParentView).showActionBar()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
        dismissDateDialog()
        dismissNumberDialog()
    }

    private fun dismissDateDialog() =
            mDateDialog?.run { if (isShowing) dismiss() }

    private fun dismissNumberDialog() =
            mNumberDialog?.run { if (isShowing) dismiss() }

    override fun doOnError(error: Throwable) {
        super.doOnError(error)
        stopProgress()

        error.printStackTrace()

        val gson = Gson()

        val errorBody = (error as? HttpException)?.response()?.errorBody()
        if (errorBody != null) {
            val response = gson.fromJson(InputStreamReader(errorBody.byteStream()), BaseResponse::class.java)

            val errors = response.errors
            val key = errors.keys.first()

            mView?.showToast("${getFieldName(key)}: ${errors[key]?.firstOrNull() ?: FieldNames.UNKNOWN_ERROR}")

        } else {
            mView?.showToast(getContext().getString(R.string.registration_error))
        }
    }

    private fun getFieldName(key: String): String =
            mView?.getContext()?.getString(FieldNames.registrationFieldnames(key, R.string.registration)) ?: ""

    override fun singUp(fullName: String, workOrLearnPlace: String, position: String,
                        district: String, unionist: Boolean, numberOfUnionTicket: String,
                        email: String, phoneNumber: String, password: String) {

        if (checkSingUpData(fullName = fullName, workOrLearnPlace = workOrLearnPlace, position = position,
                district = district, unionist = unionist, numberOfUnionTicket = numberOfUnionTicket,
                email = email, phoneNumber = phoneNumber, password = password)) {

            val singUpData = SingUpData(
                    email = email,
                    full_name = fullName,
                    birthday = mBirthDate?.formatServerDate ?: "",
                    password = password,
                    work_or_learn_place = workOrLearnPlace,
                    position = position,
                    experience = mExperience,
                    district = district,
                    unionist = unionist,
                    number_of_union_ticket = numberOfUnionTicket,
                    phone_number = phoneNumber)

            addDissposable(authInteractor.singUp(singUpData)
                    .doOnSubscribe { startProgress() }
                    .subscribe(this::doOnSingUp, this::doOnError))
        }
    }

    private fun doOnSingUp(result: Monade) {
        stopProgress()
        if (!result.isError)
            mView!!.showAccountCreatedDialog()
    }

    override fun pickDate() = mView?.run {
        dismissDateDialog()
        mDateDialog = DialogUtils.showDateDialog(getContext(), mBirthDate, null, Date()) {
            mBirthDate = it
            setDate(it.formatDisplayDate)
        }
    }

    override fun pickExperience() = mView?.run {
        mNumberDialog = DialogUtils.showNumberDialog(getContext(), R.string.experience, R.string.input_experience) {
            mExperience = it
            setExperience(it)
        }
    }

    private fun checkSingUpData(fullName: String, workOrLearnPlace: String, position: String,
                                district: String, unionist: Boolean, numberOfUnionTicket: String,
                                email: String, phoneNumber: String, password: String): Boolean {

        if (!phoneNumber.isBlank() && phoneNumber.length < 18) {
            mView!!.showToast(getContext().getString(R.string.input_phone))
            return false
        }

        if (!EmailValidator.getInstance().isValid(email)) {
            mView!!.showToast(getContext().getString(R.string.error_enter_valid_email))
            return false
        }

        if (TextUtils.isEmpty(password)) {
            mView!!.showToast(getContext().getString(R.string.indicate_password))
            return false
        }
        if (TextUtils.isEmpty(fullName)) {
            mView!!.showToast(getContext().getString(R.string.indicate_full_name))
            return false
        }
        if (mBirthDate == null) {
            mView!!.showToast(getContext().getString(R.string.indicate_birthday))
            return false
        }
        if (TextUtils.isEmpty(workOrLearnPlace)) {
            mView!!.showToast(getContext().getString(R.string.indicate_work_or_learn_place))
            return false
        }
        if (mExperience <= 0) {
            mView!!.showToast(getContext().getString(R.string.indicate_experience))
            return false
        }

        if (unionist) {
            if (numberOfUnionTicket.isBlank()) {
                mView?.showToast(getContext().getString(R.string.indicate_number_of_unionist_ticket))
                return false
            } else if (numberOfUnionTicket.length !in 6..8) {
                mView?.showToast(getContext().getString(R.string.wrong_unionist_ticket))
                return false
            }
        }

        if (district.isBlank()) {
            mView?.showToast(getContext().getString(R.string.indicate_district))
            return false
        }
        return true
    }

    private fun startProgress() {
        mView?.getParentView()?.startProgress()
        mView?.lockUi()
    }

    private fun stopProgress() {
        mView?.getParentView()?.stopProgress()
        mView?.unlockUi()
    }
}