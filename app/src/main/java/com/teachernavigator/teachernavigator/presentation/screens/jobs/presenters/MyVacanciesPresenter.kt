package com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.os.Bundle
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IJobInteractor
import com.teachernavigator.teachernavigator.presentation.models.VacancyModel
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.VacancyFragment
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.abstractions.MyJobsView
import com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters.abstractions.IMyVacanciesPresenter
import com.teachernavigator.teachernavigator.presentation.transformers.VacancyTransformer
import com.teachernavigator.teachernavigator.presentation.transformers.transformListEntity
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by lliepmah on 24.09.17
 */
@PerParentScreen
class MyVacanciesPresenter
@Inject constructor(val router: Router,
                    private val jobsInteractor: IJobInteractor,
                    private val vacancyTransformer: VacancyTransformer) : BasePresenter<MyJobsView>(), IMyVacanciesPresenter {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        mView?.getParentView()?.setToolbarTitle(R.string.my_vacancy)
        loadMyJobs()
    }

    override fun refresh() {
        mView?.showRefresh()
        loadMyJobs()
    }

    private fun loadMyJobs() =
            addDissposable(jobsInteractor.loadMyVacancies()
                    .transformListEntity(vacancyTransformer)
                    .doOnSubscribe { startProgress() }
                    .subscribe(this::onLoaded, this::onError))


    private fun onUpdated(stub: Unit) {
        refresh()
    }

    private fun onLoaded(listOfVacancy: List<VacancyModel>) {
        stopProgress()
        mView?.setJobs(listOfVacancy)
    }

    private fun onError(error: Throwable) {
        stopProgress()
        error.printStackTrace()
        doOnError(error)
    }

    private fun startProgress() = mView?.apply {
        getParentView().startProgress()
        showRefresh()
    }

    private fun stopProgress() = mView?.apply {
        getParentView().stopProgress()
        hideRefresh()
    }

    override fun onDelete(vacancy: VacancyModel) =
            addDissposable(jobsInteractor.removeVacancy(vacancy.id)
                    .doOnSubscribe { startProgress() }
                    .subscribe(this::onUpdated, this::onError))


    override fun onProlong(vacancy: VacancyModel) =
            addDissposable(jobsInteractor.prolongVacancy(vacancy.id)
                    .doOnSubscribe { startProgress() }
                    .subscribe(this::onUpdated, this::onError))

    override fun onChoose(vacancy: VacancyModel) {
        val bundle = Bundle()
        bundle.putInt(VacancyFragment.VACANCY_ID, vacancy.id)
        router.navigateTo(VacancyFragment.FRAGMENT_KEY, bundle)
    }


}