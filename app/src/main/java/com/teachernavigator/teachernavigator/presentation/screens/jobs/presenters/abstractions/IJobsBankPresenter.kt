package com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters.abstractions

import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.abstractions.JobsBankView

/**
 *  @author Arthur Korchagin on 19.09.17.
 */
interface IJobsBankPresenter : ViewAttacher<JobsBankView> {

    fun navigateToJobs()

    fun setApplicant()
    fun setEmployer()

    fun navigateToMyJobs()
    fun navigateToPostJob()
    fun navigateToViewResume()
    fun navigateToMyResume()
    fun navigateToViewProposals()

}