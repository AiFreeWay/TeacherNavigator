package com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments

import android.os.Bundle
import android.support.transition.Fade
import android.support.transition.TransitionManager
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.components.DaggerParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.components.ParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.modules.ParentScreenModule
import com.teachernavigator.teachernavigator.application.utils.rootComponent
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.abstractions.JobsBankView
import com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters.abstractions.IJobsBankPresenter
import kotlinx.android.synthetic.main.fmt_jobs_bank.*
import javax.inject.Inject

/**
 *  @author Arthur Korchagin on 19.09.17
 */

class JobsBankFragment : BaseFragment(), JobsBankView {

    companion object {
        val FRAGMENT_KEY = "jobs_bank_fragment"
    }

    private lateinit var mParentScreenComponent: ParentScreenComponent

    @Inject
    lateinit var jobsBankPresenter: IJobsBankPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fmt_jobs_bank, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        jobsBankCardProposal.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(context, R.drawable.ic_briefcase), null, null)
        jobsBankCardApplicant.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(context, R.drawable.ic_applicant), null, null)

        jobsBankCardApplicant.setOnClickListener { jobsBankPresenter.setApplicant() }
        jobsBankCardProposal.setOnClickListener { jobsBankPresenter.setEmployer() }

        jobsBankMyJobs.setOnClickListener { jobsBankPresenter.navigateToMyJobs() }
        jobsBankPostJob.setOnClickListener { jobsBankPresenter.navigateToPostJob() }
        jobsBankViewResume.setOnClickListener { jobsBankPresenter.navigateToViewResume() }
        jobsBankMyResume.setOnClickListener { jobsBankPresenter.navigateToMyResume() }
        jobsBankViewProposals.setOnClickListener { jobsBankPresenter.navigateToViewProposals() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        inject()
        jobsBankPresenter.attachView(this)
    }

    private fun inject() {
        mParentScreenComponent = DaggerParentScreenComponent.builder()
                .rootComponent(rootComponent())
                .parentScreenModule(ParentScreenModule(getParentView()))
                .build()
                .also { it.inject(this) }
    }

    override fun setState(applicant: Boolean, initial: Boolean) {
        if (!initial) {
            TransitionManager.beginDelayedTransition(jobsBankMyJobs.rootView as ViewGroup, Fade().setDuration(500))
        }

        val applicantVisibility = if (applicant) View.VISIBLE else View.GONE
        val employerVisibility = if (!applicant) View.VISIBLE else View.GONE

        jobsBankMyJobs.visibility = employerVisibility
        jobsBankPostJob.visibility = employerVisibility
        jobsBankViewResume.visibility = employerVisibility

        jobsBankMyResume.visibility = applicantVisibility
        jobsBankViewProposals.visibility = applicantVisibility

        jobsBankCardApplicant.background = ContextCompat.getDrawable(context, if (applicant) R.drawable.bg_card else android.R.color.transparent)
        jobsBankCardProposal.background = ContextCompat.getDrawable(context, if (!applicant) R.drawable.bg_card else android.R.color.transparent)

        jobsBankBtnProposalChosen.visibility = employerVisibility
        jobsBankBtnApplicantChosen.visibility = applicantVisibility

    }


}