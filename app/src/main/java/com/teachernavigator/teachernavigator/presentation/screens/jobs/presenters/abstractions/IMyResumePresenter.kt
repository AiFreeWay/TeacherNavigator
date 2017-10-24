package com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters.abstractions

import com.teachernavigator.teachernavigator.presentation.models.ResumeModel
import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.abstractions.MyResumeView

/**
 * Created by lliepmah on 24.09.17
 */
interface IMyResumePresenter : ViewAttacher<MyResumeView> {

    fun refresh()

    fun onProlong(resume : ResumeModel)
    fun onDelete(resume : ResumeModel)
    fun openResume(resume : String)
    fun createResume()
}