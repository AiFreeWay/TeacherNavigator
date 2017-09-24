package com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.abstractions

import com.teachernavigator.teachernavigator.presentation.screens.common.ChildView

/**
 * Created by lliepmah on 25.09.17
 */
interface CreateResumeView : ChildView {
    fun errorValidation(validationResId: Int, fieldLabelResId: Int)
}