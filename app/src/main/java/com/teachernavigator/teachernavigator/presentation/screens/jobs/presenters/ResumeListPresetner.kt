package com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IJobInteractor
import com.teachernavigator.teachernavigator.presentation.models.ResumeModel
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.abstractions.ResumeListView
import com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters.abstractions.IResumeListPresetner
import com.teachernavigator.teachernavigator.presentation.transformers.ResumeTransformer
import com.teachernavigator.teachernavigator.presentation.transformers.transformListEntity
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by lliepmah on 27.09.17
 */
@PerParentScreen
class ResumeListPresetner
@Inject constructor(val router: Router,
                    private val resumeTransformer: ResumeTransformer,
                    private val jobsInteractor: IJobInteractor) : BasePresenter<ResumeListView>(), IResumeListPresetner {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        mView?.getParentView()?.setToolbarTitle(R.string.view_resume)
        loadResumeList()
    }

    override fun refresh() {
        loadResumeList()
    }

    private fun loadResumeList() =
            addDissposable(jobsInteractor.loadResumeList()
                    .transformListEntity(resumeTransformer)
                    .doOnSubscribe { startProgress() }
                    .subscribe(this::onLoaded, this::onError))

    private fun onLoaded(listOfResume: List<ResumeModel>) {
        stopProgress()
        mView?.setResumes(listOfResume)
    }

    private fun onError(e: Throwable) {
        stopProgress()
        doOnError(e)
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