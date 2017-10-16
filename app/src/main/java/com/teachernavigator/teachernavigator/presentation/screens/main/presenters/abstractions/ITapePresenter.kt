package com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions

import android.support.v4.app.Fragment
import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.TapeView

/**
 * Created by root on 17.08.17
 */
interface ITapePresenter : ViewAttacher<TapeView> {

    fun loadFragments(type : Int?)
    fun openPostSearchScreen()
    fun refresh(fragment: Fragment?)
}