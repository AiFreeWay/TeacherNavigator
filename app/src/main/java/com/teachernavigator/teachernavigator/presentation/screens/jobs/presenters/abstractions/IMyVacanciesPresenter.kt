package com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters.abstractions

import com.teachernavigator.teachernavigator.presentation.models.VacancyModel
import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.abstractions.MyJobsView

/**
 * Created by lliepmah on 24.09.17
 */
interface IMyVacanciesPresenter : ViewAttacher<MyJobsView> {

    fun onProlong(vacancy: VacancyModel)
    fun onDelete(vacancy: VacancyModel)
    fun refresh()
}