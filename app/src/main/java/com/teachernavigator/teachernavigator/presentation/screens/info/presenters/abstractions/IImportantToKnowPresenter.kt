package com.teachernavigator.teachernavigator.presentation.screens.info.presenters.abstractions

import com.teachernavigator.teachernavigator.presentation.models.Info
import com.teachernavigator.teachernavigator.presentation.models.PostModel
import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.info.fragments.abstractions.ImportantToKnowView

/**
 * Created by lliepmah on 05.10.17
 */
interface IImportantToKnowPresenter : ViewAttacher<ImportantToKnowView> {

    fun refresh()

    fun onThemeChanged(infoTheme: Info)

}