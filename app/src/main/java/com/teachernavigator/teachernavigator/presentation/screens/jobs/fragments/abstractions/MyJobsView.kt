package com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.abstractions

import com.teachernavigator.teachernavigator.domain.models.Vacancy
import com.teachernavigator.teachernavigator.presentation.models.VacancyModel
import com.teachernavigator.teachernavigator.presentation.screens.common.ChildView

/**
 * Created by lliepmah on 24.09.17
 */
interface MyJobsView : ChildView {

    fun setJobs(jobsList: List<VacancyModel>)

    fun hideRefresh()
    fun showRefresh()
}