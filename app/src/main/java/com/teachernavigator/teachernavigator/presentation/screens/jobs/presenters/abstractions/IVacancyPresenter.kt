package com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters.abstractions

import com.teachernavigator.teachernavigator.presentation.models.ResponseModel
import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.abstractions.VacancyView

/**
 * Created by lliepmah on 28.09.17
 */
interface IVacancyPresenter : ViewAttacher<VacancyView> {

    fun loadVacancy(vacancyId: Int)
    fun refresh()
    fun onDownload(response : ResponseModel)
    fun onUser(response : ResponseModel)

}