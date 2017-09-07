package com.teachernavigator.teachernavigator.presentation.menu.binders

import android.view.ViewGroup
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.models.MenuData
import com.teachernavigator.teachernavigator.presentation.models.MenuItem

/**
 * Created by root on 28.08.17.
 */
class HeaderBinder(viewGroup: ViewGroup) : BaseMenuBinder(viewInflater(viewGroup, R.layout.v_header_binder)) {

    init {
        ButterKnife.bind(this, mView)
    }

    override fun bind(menuItem: MenuItem) {
        mView.setOnClickListener {
            val action = MenuData<Any>(menuItem.mType, null)
            mOutputChannel?.onNext(action)
        }
    }
}