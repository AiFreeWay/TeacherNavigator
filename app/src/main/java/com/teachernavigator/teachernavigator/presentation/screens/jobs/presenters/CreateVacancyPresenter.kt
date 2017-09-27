package com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v7.app.AlertDialog
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.data.network.requests.VacancyRequest
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IJobInteractor
import com.teachernavigator.teachernavigator.domain.models.TypeOfEmployment
import com.teachernavigator.teachernavigator.domain.models.Vacancy
import com.teachernavigator.teachernavigator.presentation.dialogs.DialogRouter
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.abstractions.CreateVacancyView
import com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters.abstractions.ICreateVacancyPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by lliepmah on 22.09.17
 */
@PerParentScreen
class CreateVacancyPresenter
@Inject constructor(val router: Router,
                    private val dialogRouter: DialogRouter,
                    private val jobInteractor: IJobInteractor) : BasePresenter<CreateVacancyView>(), ICreateVacancyPresenter {


    private var mDialog: AlertDialog? = null
    private var mTypeOfEmployment: TypeOfEmployment? = null
    private val mTypesOfEmployment by lazy(jobInteractor::getTypesOfEmployment)

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() =
            mView?.getParentView()?.setToolbarTitle(R.string.create_vacancy)

    override fun chooseTypeOfEmployment() {


        mDialog = dialogRouter.createAlertDialog()
                .setTitle(R.string.type_of_employment)
                .setItems(mTypesOfEmployment.map { it.name }.toTypedArray()) { _, pos ->
                    onTypeOfEmploymentChosen(mTypesOfEmployment[pos])
                }
                .setCancelable(false)
                .show()
    }

    private fun onTypeOfEmploymentChosen(typeOfEmployment: TypeOfEmployment) {
        mTypeOfEmployment = typeOfEmployment
        mView?.setTypeOfEmployment(typeOfEmployment.name)
    }

    override fun validateAndCreate(organizationName: CharSequence,
                                   job: CharSequence,
                                   salary: CharSequence,
                                   city: CharSequence,
                                   requiredExperience: CharSequence,
                                   responsibilities: CharSequence,

                                   isSchool: Boolean,
                                   isCollege: Boolean,
                                   isUniversity: Boolean) = mView?.run {

        val typeOfEmploymentId = mTypeOfEmployment?.id

        when {

            organizationName.isBlank() -> errorValidation(R.string.validation_empty_field, R.string.organization_name)
            job.isBlank() -> errorValidation(R.string.validation_empty_field, R.string.vacancy)
            salary.isBlank() -> errorValidation(R.string.validation_empty_field, R.string.salary)
            salary.toString().toIntOrNull() == null -> errorValidation(R.string.validation_only_numbers, R.string.salary)
            city.isBlank() -> errorValidation(R.string.validation_empty_field, R.string.city)
            requiredExperience.isBlank() -> errorValidation(R.string.validation_empty_field, R.string.required_experience)
            typeOfEmploymentId == null -> errorValidation(R.string.validation_empty_field, R.string.type_of_employment)
            !(isSchool || isCollege || isUniversity) -> errorValidation(R.string.validation_empty, R.string.choose_type_of_institution)

            else -> createVacancy(createRequest(
                    organizationName = organizationName.toString(),
                    salary = salary.toString().toInt(),
                    vacancy = job.toString(),
                    city = city.toString(),
                    experience = requiredExperience.toString(),
                    responsibilities = responsibilities.toString(),
                    typeOfEmployment = typeOfEmploymentId,
                    typeOfSchool = getTypeNumber(isSchool, isCollege, isUniversity)
            ))
        }
    } ?: Unit

    private fun createRequest(organizationName: String, salary: Int, vacancy: String,
                              city: String, experience: String, responsibilities: String,
                              typeOfEmployment: Int, typeOfSchool: Int) =
            VacancyRequest(
                    name_of_the_organization = organizationName,
                    amount_of_wages = salary,
                    vacancy = vacancy,
                    city = city,
                    experience = experience,
                    responsibility = responsibilities,
                    type_of_employment = typeOfEmployment,
                    type_of_scool = typeOfSchool)

    private fun createVacancy(vacancyRequest: VacancyRequest) =
            addDissposable(jobInteractor.createVacancy(vacancyRequest)
                    .doOnSubscribe { startProgress() }
                    .subscribe(this::onCreated, this::onError))


    private fun getTypeNumber(school: Boolean, college: Boolean, university: Boolean) =
            when {
                school -> Vacancy.INSTITUTION_SCHOOL
                college -> Vacancy.INSTITUTION_COLLEDGE
                university -> Vacancy.INSTITUTION_UNIVERSITY
                else -> -1
            }

    private fun onError(error: Throwable) {
        error.printStackTrace()
        stopProgress()

    }

    private fun onCreated(vacancy: Vacancy) {
        stopProgress()
        mView?.showToast(R.string.vacancy_successfully_created)
        router.exit()
    }

    private fun startProgress() {
        mView!!.getParentView().startProgress()
    }

    private fun stopProgress() {
        mView!!.getParentView().stopProgress()
    }

    override fun detachView() {
        super.detachView()
        if (mDialog?.isShowing == true) {
            mDialog?.dismiss()
        }
    }
}