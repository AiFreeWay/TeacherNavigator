package com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.abstractions

import com.teachernavigator.teachernavigator.presentation.models.ResumeModel
import com.teachernavigator.teachernavigator.presentation.screens.common.ChildView

/**
 * Created by lliepmah on 24.09.17
 */
interface MyResumeView : ChildView {
    fun setResumes(listOfResume: List<ResumeModel>)

    fun showRefresh()
    fun hideRefresh()
}