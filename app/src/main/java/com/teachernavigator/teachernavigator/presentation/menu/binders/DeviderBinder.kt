package com.teachernavigator.teachernavigator.presentation.menu.binders

import android.view.ViewGroup
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.models.MenuItem

/**
 * Created by root on 28.08.17.
 */
class DeviderBinder(viewGroup: ViewGroup) : BaseMenuBinder(viewInflater(viewGroup, R.layout.v_devider_binder)) {

    init {
        ButterKnife.bind(this, mView)
    }

    override fun bind(menuItem: MenuItem) {}
}