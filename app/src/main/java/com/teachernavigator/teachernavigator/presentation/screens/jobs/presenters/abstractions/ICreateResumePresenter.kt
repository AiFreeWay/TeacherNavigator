package com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters.abstractions

import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.abstractions.CreateResumeView

/**
 * Created by lliepmah on 25.09.17
 */
interface ICreateResumePresenter : ViewAttacher<CreateResumeView> {
    fun validateAndCreate(
            careerObjective: CharSequence,
            districtCouncil: CharSequence,
            education: CharSequence,
            experience: CharSequence,
            salary: CharSequence
    )

}