package com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.data.network.requests.ResumeRequest
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IJobInteractor
import com.teachernavigator.teachernavigator.domain.models.Resume
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.abstractions.CreateResumeView
import com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters.abstractions.ICreateResumePresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by lliepmah on 25.09.17
 */

@PerParentScreen
class CreateResumePresenter
@Inject constructor(val router: Router,
                    private val jobInteractor: IJobInteractor) : BasePresenter<CreateResumeView>(), ICreateResumePresenter {

    override var resumePath: String? = null
    override var resumeMime: String? = null

    override fun validateAndCreate(careerObjective: CharSequence,
                                   districtCouncil: CharSequence,
                                   education: CharSequence,
                                   experience: CharSequence,
                                   salary: CharSequence
    ) = mView?.run {
        when {
            careerObjective.isBlank() -> errorValidation(R.string.validation_empty_field, R.string.career_objective)
            districtCouncil.isBlank() -> errorValidation(R.string.validation_empty_field, R.string.district_council)
            districtCouncil.toString().toIntOrNull() == null -> errorValidation(R.string.validation_only_numbers, R.string.district_council)
            education.isBlank() -> errorValidation(R.string.validation_empty_field, R.string.education)
            experience.isBlank() -> errorValidation(R.string.validation_empty_field, R.string.experience)
            salary.isBlank() -> errorValidation(R.string.validation_empty_field, R.string.salary)

            else -> createResume(ResumeRequest(
                    careerObjective = careerObjective.toString(),
                    districtCouncil = districtCouncil.toString().toInt(),
                    education = education.toString(),
                    experience = experience.toString(),
                    salary = salary.toString(),
                    resumePath = resumePath,
                    mime = resumeMime

            ))
        }
    } ?: Unit

    private fun createResume(resumeRequest: ResumeRequest) =
            addDissposable(jobInteractor.createResume(resumeRequest)
                    .doOnSubscribe { startProgress() }
                    .subscribe(this::onCreated, this::onError))

    private fun onError(error: Throwable) {
        error.printStackTrace()
        stopProgress()

    }

    private fun onCreated(resume: Resume) {
        stopProgress()
        mView?.showToast(R.string.resume_successfully_created)
        router.exit()
    }


    private fun startProgress() =
            mView?.getParentView()?.startProgress()

    private fun stopProgress() =
            mView?.getParentView()?.stopProgress()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() =
            mView?.getParentView()?.setToolbarTitle(R.string.create_resume)

}