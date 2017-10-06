package com.teachernavigator.teachernavigator.presentation.screens.info.presenters.abstractions

import com.teachernavigator.teachernavigator.presentation.models.Specialist
import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.info.fragments.abstractions.AskSpecialistView

/**
 * Created by lliepmah on 05.10.17
 */
interface IAskSpecialistPresenter : ViewAttacher<AskSpecialistView> {

    var specialist: Specialist?

    fun askQuestion(message: String)

}