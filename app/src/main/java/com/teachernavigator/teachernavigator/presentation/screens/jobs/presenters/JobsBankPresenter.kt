package com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters

import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
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

    var isApplicant = false

    override fun attachView(view: JobsBankView) {
        super.attachView(view)
        mView?.setState(isApplicant, true)
    }

    override fun navigateToJobs() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setApplicant() {
        isApplicant = true
        mView?.setState(isApplicant, false)
    }

    override fun setEmployer() {
        isApplicant = false
        mView?.setState(isApplicant, false)
    }

    override fun navigateToMyJobs() {
    }

    override fun navigateToPostJob() {
    }

    override fun navigateToViewResume() {
    }

    override fun navigateToMyResume() {
    }

    override fun navigateToViewProposals() {
    }

}