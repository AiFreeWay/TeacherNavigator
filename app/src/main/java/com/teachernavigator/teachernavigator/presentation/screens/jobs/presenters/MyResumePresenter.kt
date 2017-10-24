package com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IJobInteractor
import com.teachernavigator.teachernavigator.presentation.models.ResumeModel
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.CreateResumeFragment
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.abstractions.MyResumeView
import com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters.abstractions.IMyResumePresenter
import com.teachernavigator.teachernavigator.presentation.transformers.ResumeTransformer
import com.teachernavigator.teachernavigator.presentation.transformers.transformListEntity
import com.teachernavigator.teachernavigator.presentation.utils.openUrl
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by lliepmah on 24.09.17
 */
@PerParentScreen
class MyResumePresenter
@Inject constructor(val router: Router,
                    private val resumeTransformer: ResumeTransformer,
                    private val jobsInteractor: IJobInteractor) : BasePresenter<MyResumeView>(), IMyResumePresenter {

    override fun openResume(resume: String) {
        mView?.openUrl(resume)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        mView?.getParentView()?.setToolbarTitle(R.string.my_resume)
        loadMyResume()
    }

    override fun refresh() {
        loadMyResume()
    }

    private fun loadMyResume() =
            addDissposable(jobsInteractor.loadMyResume()
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

    override fun createResume() =
            router.navigateTo(CreateResumeFragment.FRAGMENT_KEY)

    override fun onProlong(resume: ResumeModel) =
            addDissposable(jobsInteractor.prolongResume(resume.id)
                    .doOnSubscribe { startProgress() }
                    .subscribe(this::onUpdated, this::onError))

    private fun onUpdated(stub: Unit) = refresh()

    override fun onDelete(resume: ResumeModel) =
            addDissposable(jobsInteractor.removeResume(resume.id)
                    .doOnSubscribe { startProgress() }
                    .subscribe(this::onUpdated, this::onError))

}