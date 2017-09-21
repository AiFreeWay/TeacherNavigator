package com.teachernavigator.teachernavigator.presentation.menu.binders

import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.models.MenuData
import com.teachernavigator.teachernavigator.presentation.models.MenuItem

/**
 * Created by root on 28.08.17.
 */
class BottomBinder(viewGroup: ViewGroup) : BaseMenuBinder(viewInflater(viewGroup, R.layout.v_bottom_biner)) {

    @BindView(R.id.v_bottom_binder_btn_add_publication)
    lateinit var mBtnAddPublication: Button

    init {
        ButterKnife.bind(this, mView)
    }

    override fun bind(menuItem: MenuItem) {
        mBtnAddPublication.setOnClickListener {
            val action = MenuData<Any>(menuItem.mType, null)
            mOutputChannel?.onNext(action)
        }
    }
}