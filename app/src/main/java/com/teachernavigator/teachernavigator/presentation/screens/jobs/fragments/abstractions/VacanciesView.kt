package com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.abstractions

import com.teachernavigator.teachernavigator.presentation.models.VacancyModel
import com.teachernavigator.teachernavigator.presentation.screens.common.ChildView

/**
 * Created by lliepmah on 27.09.17
 */
interface VacanciesView : ChildView {

    fun setJobs(jobsList: List<VacancyModel>)

    fun hideRefresh()
    fun showRefresh()
}