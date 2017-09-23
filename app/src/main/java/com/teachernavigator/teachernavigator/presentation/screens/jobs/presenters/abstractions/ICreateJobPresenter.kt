package com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters.abstractions

import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.abstractions.CreateJobView

/**
 *  @author Arthur Korchagin on 19.09.17.
 */
interface ICreateJobPresenter : ViewAttacher<CreateJobView> {
    fun validateAndCreate(
            organizationName: CharSequence,
            job: CharSequence,
            salary: CharSequence,
            city: CharSequence,
            requiredExperience: CharSequence,
            responsibilities: CharSequence,

            isSchool: Boolean,
            isCollege: Boolean,
            isUniversity: Boolean
    )

    fun chooseTypeOfEmployment()


}