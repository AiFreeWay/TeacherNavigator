package com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters.abstractions

import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.abstractions.ResumeListView

/**
 * Created by lliepmah on 27.09.17
 */
interface IResumeListPresetner : ViewAttacher<ResumeListView> {

    fun refresh()
}