package com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.components.DaggerParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.components.ParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.modules.ParentScreenModule
import com.teachernavigator.teachernavigator.application.utils.rootComponent
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.abstractions.CreateVacancyView
import com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters.abstractions.ICreateVacancyPresenter
import kotlinx.android.synthetic.main.fmt_create_vacancy.*
import javax.inject.Inject

/**
 * Created by lliepmah on 22.09.17
 */

class CreateVacancyFragment : BaseFragment(), CreateVacancyView {

    companion object {
        val FRAGMENT_KEY = "create_vacancy_fragment"
    }

    private lateinit var mParentScreenComponent: ParentScreenComponent

    @Inject
    lateinit var createVacancyPresenter: ICreateVacancyPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fmt_create_vacancy, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createJobBtnCreateJob.setOnClickListener { createJob() }
        createJobEtTypeOfEmployment.setOnClickListener { createVacancyPresenter.chooseTypeOfEmployment() }

    }

    override fun setTypeOfEmployment(type: CharSequence) {
        createJobEtTypeOfEmployment.text = type
    }

    private fun createJob() =
            createVacancyPresenter.validateAndCreate(
                    organizationName = createJobEtOrganizationName.text,
                    job = createJobEtJob.text,
                    salary = createJobEtSalary.text,
                    city = createJobEtCity.text,
                    requiredExperience = createJobEtRequiredExperience.text,
                    responsibilities = createJobEtResponsibilities.text,

                    isSchool = createJobChSchool.isChecked,
                    isCollege = createJobChCollege.isChecked,
                    isUniversity = createJobChUniversity.isChecked
            )

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        inject()
        createVacancyPresenter.attachView(this)
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