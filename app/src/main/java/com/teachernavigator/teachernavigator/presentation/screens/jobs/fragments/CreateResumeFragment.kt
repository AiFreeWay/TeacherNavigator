package com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments

import android.os.Bundle
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
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.abstractions.CreateResumeView
import com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters.abstractions.ICreateResumePresenter
import kotlinx.android.synthetic.main.fmt_create_resume.*
import javax.inject.Inject

/**
 * Created by lliepmah on 25.09.17
 */

class CreateResumeFragment : BaseFragment(), CreateResumeView {

    companion object {
        val FRAGMENT_KEY = "create_resume_fragment"
    }

    private lateinit var mParentScreenComponent: ParentScreenComponent

    @Inject
    lateinit var createResumePresenter: ICreateResumePresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fmt_create_resume, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createResumeTvAttachResume.setCompoundDrawables(null, null, ContextCompat.getDrawable(context, R.drawable.ic_resume), null)
        createResumeBtnCreate.setOnClickListener { createResume() }
    }

    private fun createResume() =
            createResumePresenter.validateAndCreate(
                    careerObjective = createResumeEtCareerObjective.text,
                    districtCouncil = createResumeEtDistrictCouncil.text,
                    education = createResumeEtEducation.text,
                    experience = createResumeEtExperience.text
            )

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        inject()
        createResumePresenter.attachView(this)
    }

    private fun inject() {
        mParentScreenComponent = DaggerParentScreenComponent.builder()
                .rootComponent(rootComponent())
                .parentScreenModule(ParentScreenModule(getParentView()))
                .build()
                .also { it.inject(this) }
    }

    override fun errorValidation(validationResId: Int, fieldLabelResId: Int) =
            showToast(getString(validationResId, getString(fieldLabelResId)))

}