package com.teachernavigator.teachernavigator.presentation.screens.auth.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.text.TextUtils
import com.example.root.androidtest.application.utils.Logger
import com.google.gson.Gson
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.data.network.responses.BaseResponse
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IAuthInteractor
import com.teachernavigator.teachernavigator.domain.models.Monade
import com.teachernavigator.teachernavigator.domain.models.SingUpData
import com.teachernavigator.teachernavigator.presentation.screens.auth.activities.abstractions.AuthParentView
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.abstractions.RegistrationView
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.abstractions.IRegistrationPresenter
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.utils.DialogUtils
import com.teachernavigator.teachernavigator.presentation.utils.formatDisplayDate
import com.teachernavigator.teachernavigator.presentation.utils.formatServerDatetime
import com.teachernavigator.teachernavigator.presentation.validators.EmailValidator
import retrofit2.HttpException
import java.io.InputStreamReader
import java.util.*
import javax.inject.Inject

/**
 * Created by root on 24.08.17
 */
class FmtRegistrationPresenter : BasePresenter<RegistrationView>(), IRegistrationPresenter {

    @Inject
    lateinit var mAuthInteractor: IAuthInteractor

    private var mBirthdate: Date? = Date()
    private var mExperience = 0

    init {
        Logger.logDebug("created PRESENTER FmtRegistrationPresenter")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        mView!!.getParentView().setToolbarTitle(R.string.registration)
        (mView!!.getParentView() as AuthParentView).showActionBar()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun attachView(view: RegistrationView) {
        super.attachView(view)
        inject()
    }

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

            mView!!.showToast("${getFieldName(key)}: ${errors[key]?.firstOrNull() ?: "неизвестная ошибка"}")

        } else {
            mView!!.showToast(getContext().getString(R.string.registration_error))
        }
    }

    private fun getFieldName(key: String) = when (key) {

        "email" -> mView?.getContext()?.getString(R.string.email) ?: ""
        "password" -> mView?.getContext()?.getString(R.string.password) ?: ""
        "full_name" -> mView?.getContext()?.getString(R.string.full_name) ?: ""
        "birthday" -> mView?.getContext()?.getString(R.string.birthday) ?: ""

        "work_or_learn_place" -> mView?.getContext()?.getString(R.string.work_or_learn_place) ?: ""
        "district" -> mView?.getContext()?.getString(R.string.district) ?: ""
        "position" -> mView?.getContext()?.getString(R.string.position) ?: ""

        "experience" -> mView?.getContext()?.getString(R.string.experience) ?: ""
        "unionist" -> mView?.getContext()?.getString(R.string.trade_unionist) ?: ""
        "number_of_union_ticket" -> mView?.getContext()?.getString(R.string.indicate_number_of_unionist_ticket) ?: ""
        "phone_number" -> mView?.getContext()?.getString(R.string.phone_number) ?: ""

        else -> {
            "Регистрация"
        }
    }

    override fun singUp(fullName: String, workOrLearnPlace: String, position: String,
                        unionist: Boolean, numberOfUnionTicket: String, email: String,
                        phoneNumber: String, password: String) {

        if (checkSingUpData(fullName = fullName, workOrLearnPlace = workOrLearnPlace, position = position,
                unionist = unionist, numberOfUnionTicket = numberOfUnionTicket, email = email,
                phoneNumber = phoneNumber, password = password)) {

            val singUpData = SingUpData(
                    email = email,
                    full_name = fullName,
                    birthday = mBirthdate?.formatServerDatetime ?: "",
                    password = password,
                    work_or_learn_place = workOrLearnPlace,
                    position = position,
                    experience = mExperience,
                    unionist = unionist,
                    number_of_union_ticket = numberOfUnionTicket,
                    phone_number = phoneNumber)

            addDissposable(mAuthInteractor.singUp(singUpData)
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
        DialogUtils.showDateDialog(getContext(), mBirthdate, null, Date()) {
            mBirthdate = it
            setDate(it.formatDisplayDate ?: "")
        }
    } ?: Unit

    override fun pickExperience() = mView?.run {
        DialogUtils.showNumberDialog(getContext(), R.string.experience, R.string.input_experience) {
            mExperience = it
            setExperience(getContext().resources.getQuantityString(R.plurals.plural_years, it, it))
        }

        Unit
    } ?: Unit

    private fun checkSingUpData(fullName: String, workOrLearnPlace: String, position: String,
                                unionist: Boolean, numberOfUnionTicket: String, email: String,
                                phoneNumber: String, password: String): Boolean {

        if (phoneNumber.isBlank() && email.isBlank()) {
            mView!!.showToast(getContext().getString(R.string.input_phone_or_email))
            return false
        }

        if (email.isBlank() && phoneNumber.length < 18) {
            mView!!.showToast(getContext().getString(R.string.input_phone))
            return false
        }

        if (phoneNumber.isBlank() && !EmailValidator.getInstance().isValid(email)) {
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
        if (mBirthdate == null) {
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
        if (unionist && TextUtils.isEmpty(numberOfUnionTicket)) {
            mView!!.showToast(getContext().getString(R.string.indicate_number_of_unionist_ticket))
            return false
        }
        return true
    }

    private fun inject() {
        mView!!.getParentView()
                .getParentScreenComponent()
                .inject(this)
    }

    private fun startProgress() {
        mView!!.getParentView().startProgress()
        mView!!.lockUi()
    }

    private fun stopProgress() {
        mView!!.getParentView().stopProgress()
        mView!!.unlockUi()
    }
}