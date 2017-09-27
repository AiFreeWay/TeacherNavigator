package com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.abstractions

import android.support.annotation.StringRes
import com.teachernavigator.teachernavigator.presentation.screens.common.ChildView

/**
 * Created by lliepmah on 22.09.17
 */
interface CreateVacancyView : ChildView {

    fun setTypeOfEmployment(type: CharSequence)

    fun errorValidation(@StringRes validationResId: Int, @StringRes fieldLabelResId: Int)

}