package com.teachernavigator.teachernavigator.presentation.screens.info.presenters.abstractions

import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.info.fragments.abstractions.SupportView

/**
 * Created by lliepmah on 05.10.17
 */
interface ISupportPresenter : ViewAttacher<SupportView> {

    fun openLegalInspector()
    fun openEconomist()
    fun openLabourProtection()
    fun openMethodist()
    fun openPsychologist()

}