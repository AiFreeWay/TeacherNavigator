package com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters.abstractions

import com.teachernavigator.teachernavigator.presentation.models.VacancyModel
import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.abstractions.VacanciesView

/**
 * Created by lliepmah on 27.09.17
 */
interface IVacanciesPresenter : ViewAttacher<VacanciesView> {

    fun setFilter(isSchool: Boolean, isCollege: Boolean, isUniversity: Boolean)
    fun refresh()

    fun onResponse(vacancy : VacancyModel)
}