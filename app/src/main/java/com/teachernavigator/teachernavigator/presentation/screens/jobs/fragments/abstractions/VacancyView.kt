package com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.abstractions

import com.teachernavigator.teachernavigator.presentation.models.VacancyModel
import com.teachernavigator.teachernavigator.presentation.screens.common.ChildView

/**
 * Created by lliepmah on 28.09.17
 */
interface VacancyView : ChildView {

    fun setVacancy(vacancy: VacancyModel)

    fun hideRefresh()
    fun showRefresh()
}