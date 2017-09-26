package com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.abstractions

import com.teachernavigator.teachernavigator.presentation.models.ResumeModel
import com.teachernavigator.teachernavigator.presentation.screens.common.ChildView

/**
 * Created by lliepmah on 27.09.17
 */
interface ResumeListView : ChildView {
    fun setResumes(listOfResume: List<ResumeModel>)

    fun showRefresh()
    fun hideRefresh()
}