package com.teachernavigator.teachernavigator.presentation.menu.binders

import android.view.ViewGroup
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.models.MenuItem

/**
 * Created by root on 28.08.17
 */
class DividerBinder(viewGroup: ViewGroup) : BaseMenuBinder(viewInflater(viewGroup, R.layout.v_divider_binder)) {

    override fun bind(menuItem: MenuItem) {}
}