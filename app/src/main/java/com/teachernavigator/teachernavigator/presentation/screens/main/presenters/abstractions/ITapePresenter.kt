package com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions

import com.teachernavigator.teachernavigator.presentation.screens.base.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.TapeView

/**
 * Created by root on 17.08.17.
 */
interface ITapePresenter : ViewAttacher<TapeView> {

    fun loadFragments()
}