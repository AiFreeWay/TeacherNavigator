package com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions

import com.teachernavigator.teachernavigator.presentation.models.ViewPagerItemContainer
import com.teachernavigator.teachernavigator.presentation.screens.common.ChildView

/**
 * Created by root on 18.09.17.
 */
interface SettingsView : ChildView {

    fun loadOrdersFragments(data: List<ViewPagerItemContainer>)
}