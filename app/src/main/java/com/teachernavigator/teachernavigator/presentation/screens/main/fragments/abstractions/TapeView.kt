package com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions

import com.teachernavigator.teachernavigator.presentation.models.ViewPagerItemContainer
import com.teachernavigator.teachernavigator.presentation.screens.base.ChildView

/**
 * Created by root on 14.08.17.
 */
interface TapeView : ChildView {

    fun loadOrdersFragments(data: List<ViewPagerItemContainer>)
}