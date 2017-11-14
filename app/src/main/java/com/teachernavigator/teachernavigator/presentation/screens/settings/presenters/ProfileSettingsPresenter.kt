package com.teachernavigator.teachernavigator.presentation.screens.settings.presenters

import android.app.DatePickerDialog
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.text.TextUtils
import com.afollestad.materialdialogs.MaterialDialog
import com.google.gson.Gson
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.data.network.responses.BaseResponse
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IProfileInteractor
import com.teachernavigator.teachernavigator.domain.models.EditProfileData
import com.teachernavigator.teachernavigator.presentation.models.FieldNames
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.activities.abstractions.MainView
import com.teachernavigator.teachernavigator.presentation.screens.settings.fragments.abstractions.ProfileSettingsView
import com.teachernavigator.teachernavigator.presentation.screens.settings.presenters.abstractions.IProfileSettingsPresenter
import com.teachernavigator.teachernavigator.presentation.utils.DialogUtils
import com.teachernavigator.teachernavigator.presentation.utils.formatDisplayDate
import com.teachernavigator.teachernavigator.presentation.utils.formatServerDate
import com.teachernavigator.teachernavigator.presentation.utils.parseServerDate
import com.teachernavigator.teachernavigator.presentation.validators.EmailValidator
import retrofit2.HttpException
import ru.terrakok.cicerone.Router
import java.io.InputStreamReader
import java.util.*
import javax.inject.Inject

/**
 * Created by root on 18.09.17
 */
@PerParentScreen
class ProfileSettingsPresenter
@Inject
constructor(private val router: Router,
            private val profileInteractor: IProfileInteractor) : BasePresenter<ProfileSettingsView>(), IProfileSettingsPresenter {

    private var mBirthDate: Date? = Date()
    private var mExperience = 0

    private var mDateDialog: DatePickerDialog? = null
    private var mNumberDialog: MaterialDialog? = null


    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun loadSettings() {
        startProgress()
        addDissposable(profileInteractor.getProfileSettings()
                .doOnSubscribe { startProgress() }
                .subscribe(this::onProfileLoaded, this::doOnError))
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

    private fun doOnSubscribe() {
        mView?.getParentView()?.startProgress()
    }

    override fun save(fullName: String, workOrLearnPlace: String, position: String,
                      district: String, unionist: Boolean, numberOfUnionTicket: String,
                      email: String, phoneNumber: String, password: String) {

        if (checkProfileData(fullName = fullName, workOrLearnPlace = workOrLearnPlace, position = position,
                district = district, unionist = unionist, numberOfUnionTicket = numberOfUnionTicket,
                email = email, phoneNumber = phoneNumber)) {

            val singUpData = EditProfileData(
                    email = email,
                    full_name = fullName,
                    birthday = mBirthDate?.formatServerDate ?: "",
                    password = password.takeIf { it.isNotBlank() },
                    work_or_learn_place = workOrLearnPlace.takeIf { it.isNotBlank() },
                    position = position.takeIf { it.isNotBlank() },
                    experience = mExperience,
                    district = district.takeIf { it.isNotBlank() },
                    number_of_union_ticket = numberOfUnionTicket.takeIf { it.isNotBlank() },
                    phone_number = phoneNumber.takeIf { it.isNotBlank() })

            singUpData.unionistBool = unionist

            addDissposable(profileInteractor.editProfile(singUpData)
                    .doOnSubscribe { startProgress() }
                    .subscribe(this::onProfileSaved, this::doOnError))

        }
    }

    private fun checkProfileData(fullName: String, workOrLearnPlace: String, position: String,
                                 district: String, unionist: Boolean, numberOfUnionTicket: String,
                                 email: String, phoneNumber: String): Boolean {

        if (!phoneNumber.isBlank() && phoneNumber.length < 18) {
            mView?.showToast(getContext().getString(R.string.input_phone))
            return false
        }

        if (!EmailValidator.getInstance().isValid(email)) {
            mView?.showToast(getContext().getString(R.string.error_enter_valid_email))
            return false
        }

        if (TextUtils.isEmpty(fullName)) {
            mView?.showToast(getContext().getString(R.string.indicate_full_name))
            return false
        }
        if (mBirthDate == null) {
            mView?.showToast(getContext().getString(R.string.indicate_birthday))
            return false
        }
        if (TextUtils.isEmpty(workOrLearnPlace)) {
            mView?.showToast(getContext().getString(R.string.indicate_work_or_learn_place))
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


    private fun dismissDateDialog() =
            mDateDialog?.run { if (isShowing) dismiss() }

    private fun dismissNumberDialog() =
            mNumberDialog?.run { if (isShowing) dismiss() }

    private fun startProgress() {
        mView?.getParentView()?.startProgress()
    }

    private fun stopProgress() {
        mView?.getParentView()?.stopProgress()
    }

    private fun onProfileSaved(editProfileData: EditProfileData) {
        stopProgress()
        mView?.showToast(R.string.profile_is_saved)
        onProfileLoaded(editProfileData)
    }

    private fun onProfileLoaded(editProfileData: EditProfileData) {
        stopProgress()

        val userName = editProfileData.full_name
        (mView?.getParentView() as? MainView)?.updateProfile()

        mBirthDate = editProfileData.birthday?.parseServerDate
        mExperience = editProfileData.experience ?: 0


        mView?.setDate(mBirthDate?.formatDisplayDate ?: "")
        mView?.setExperience(mExperience)
        mView?.setProfile(
                fullName = userName ?: "",
                workOrLearnPlace = editProfileData.work_or_learn_place ?: "",
                position = editProfileData.position ?: "",
                district = editProfileData.district ?: "",
                unionist = editProfileData.unionistBool,
                numberOfUnionTicket = editProfileData.number_of_union_ticket ?: "",
                email = editProfileData.email ?: "",
                phoneNumber = editProfileData.phone_number ?: ""
        )
    }

    private fun getFieldName(key: String): String =
            mView?.getContext()?.getString(FieldNames.registrationFieldnames(key, R.string.registration)) ?: ""

}