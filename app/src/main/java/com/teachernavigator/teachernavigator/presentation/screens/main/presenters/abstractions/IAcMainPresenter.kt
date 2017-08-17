package com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions

import android.support.v7.widget.RecyclerView
import com.teachernavigator.teachernavigator.presentation.screens.base.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.activities.abstractions.MainView

/**
 * Created by root on 14.08.17.
 */
abstract class IAcMainPresenter : BasePresenter<MainView>() {

    abstract fun loadMenuItemsToRecycleView(recylerView: RecyclerView)
}