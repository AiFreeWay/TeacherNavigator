package com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IJobInteractor
import com.teachernavigator.teachernavigator.presentation.models.TypeOfInstitution
import com.teachernavigator.teachernavigator.presentation.models.VacancyModel
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.abstractions.VacanciesView
import com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters.abstractions.IVacanciesPresenter
import com.teachernavigator.teachernavigator.presentation.transformers.VacancyTransformer
import com.teachernavigator.teachernavigator.presentation.transformers.transformListEntity
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by lliepmah on 27.09.17
 */
@PerParentScreen
class VacanciesPresenter
@Inject constructor(val router: Router,
                    private val jobsInteractor: IJobInteractor,
                    private val vacancyTransformer: VacancyTransformer) : BasePresenter<VacanciesView>(), IVacanciesPresenter {

    private var listOfVacancy: List<VacancyModel> = emptyList()

    private var isSchool = true
    private var isCollege = true
    private var isUniversity = true

    override fun setFilter(isSchool: Boolean, isCollege: Boolean, isUniversity: Boolean) {
        this.isSchool = isSchool
        this.isCollege = isCollege
        this.isUniversity = isUniversity
        updateJobs()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        mView?.getParentView()?.setToolbarTitle(R.string.view_vacancies)
        loadJobs()
    }

    override fun refresh() {
        mView?.showRefresh()
        loadJobs()
    }

    private fun loadJobs() =
            addDissposable(jobsInteractor.loadVacancies()
                    .transformListEntity(vacancyTransformer)
                    .doOnSubscribe { startProgress() }
                    .subscribe(this::onLoaded, this::onError))


    private fun onLoaded(listOfVacancy: List<VacancyModel>) {
        stopProgress()
        this.listOfVacancy = listOfVacancy
        updateJobs()
    }

    override fun onResponse(vacancy: VacancyModel) =
            addDissposable(jobsInteractor.respondVacancy(vacancy.id)
                    .doOnSubscribe { startProgress() }
                    .subscribe(this::onUpdated, this::onError))

    private fun onUpdated(stub: Unit) = refresh()

    private fun updateJobs() {
        mView?.setJobs(listOfVacancy.filter {
            (it.typeOfInstitution == TypeOfInstitution.SCHOOL && isSchool) ||
                    (it.typeOfInstitution == TypeOfInstitution.COLLEGE && isCollege) ||
                    (it.typeOfInstitution == TypeOfInstitution.UNIVERSITY && isUniversity)
        })
    }

    private fun onError(error: Throwable) {
        stopProgress()
        error.printStackTrace()
        doOnError(error)
    }

    private fun startProgress() {
        mView?.getParentView()?.startProgress()
        mView?.showRefresh()
    }

    private fun stopProgress() {
        mView?.getParentView()?.stopProgress()
        mView?.hideRefresh()
    }

}