package com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.abstractions;

import com.teachernavigator.teachernavigator.presentation.screens.common.ChildView

interface JobsBankView : ChildView {
    fun setState(applicant: Boolean, initial: Boolean)


}