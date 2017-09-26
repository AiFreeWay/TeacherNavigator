package com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.CreateJobFragment
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.MyJobsFragment
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.MyResumeFragment
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.ResumeListFragment
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.abstractions.JobsBankView
import com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters.abstractions.IJobsBankPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 *  @author Arthur Korchagin on 19.09.17.
 */
@PerParentScreen
class JobsBankPresenter
@Inject constructor(val router: Router) : BasePresenter<JobsBankView>(), IJobsBankPresenter {

    private var isApplicant = false

    override fun attachView(view: JobsBankView) {
        super.attachView(view)
        mView?.setState(isApplicant, true)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() =
            mView?.getParentView()?.setToolbarTitle(R.string.bank_of_vacancy)


    override fun setApplicant() {
        isApplicant = true
        mView?.setState(isApplicant, false)
    }

    override fun setEmployer() {
        isApplicant = false
        mView?.setState(isApplicant, false)
    }

    override fun navigateToMyJobs() =
            router.navigateTo(MyJobsFragment.FRAGMENT_KEY)

    override fun navigateToPostJob() =
            router.navigateTo(CreateJobFragment.FRAGMENT_KEY)

    override fun navigateToViewResume() =
            router.navigateTo(ResumeListFragment.FRAGMENT_KEY)

    override fun navigateToMyResume() =
            router.navigateTo(MyResumeFragment.FRAGMENT_KEY)

    override fun navigateToViewProposals() { // Посмотреть предложения
    }

}