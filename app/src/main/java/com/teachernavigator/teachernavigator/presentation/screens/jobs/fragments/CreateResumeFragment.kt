package com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments

import android.Manifest
import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo
import com.miguelbcr.ui.rx_paparazzo2.entities.FileData
import com.miguelbcr.ui.rx_paparazzo2.entities.Response
import com.tbruyelle.rxpermissions2.RxPermissions
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.components.DaggerParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.components.ParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.modules.ParentScreenModule
import com.teachernavigator.teachernavigator.application.utils.rootComponent
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.abstractions.CreateResumeView
import com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters.abstractions.ICreateResumePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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

    private val rxPermissions by lazy { RxPermissions(activity) }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fmt_create_resume, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createResumeTvAttachResume.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(context, R.drawable.ic_resume), null)
        createResumeBtnCreate.setOnClickListener { createResume() }
        createResumeTvAttachResume.setOnClickListener { attachFile() }

    }

    private fun attachFile() {

        rxPermissions
                .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .map { if (!it) throw Error("Permission Denied") }
                .flatMap {
                    RxPaparazzo.single(this@CreateResumeFragment)
                            .usingFiles()
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onFileAttached, this::onAttachError)


    }

    private fun onAttachError(error: Throwable) {
        error.printStackTrace()
    }

    private fun onFileAttached(response: Response<CreateResumeFragment, FileData>) {
        val data = response.data()
        if (response.resultCode() == RESULT_OK && data != null) {
            createResumeTvAttachResume.text = data.filename
            createResumePresenter.resumePath = data.file.path
            createResumePresenter.resumeMime = data.mimeType
        }
    }

    private fun createResume() =
            createResumePresenter.validateAndCreate(
                    careerObjective = createResumeEtCareerObjective.text,
                    districtCouncil = createResumeEtDistrictCouncil.text,
                    education = createResumeEtEducation.text,
                    experience = createResumeEtExperience.text,
                    salary = createResumeEtSalary.text
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