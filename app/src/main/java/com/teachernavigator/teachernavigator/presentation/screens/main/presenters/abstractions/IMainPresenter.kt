package com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import com.teachernavigator.teachernavigator.application.di.components.ParentScreenComponent
import com.teachernavigator.teachernavigator.presentation.screens.base.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.base.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.main.activities.abstractions.MainView

/**
 * Created by root on 14.08.17.
 */
interface IMainPresenter : ViewAttacher<MainView> {

    fun loadMenuItemsToRecycleView(recylerView: RecyclerView)
    fun openStartFragment(savedState: Bundle?)
    fun getParentScreenComponent(): ParentScreenComponent
}