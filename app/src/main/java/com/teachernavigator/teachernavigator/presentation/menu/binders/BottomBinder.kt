package com.teachernavigator.teachernavigator.presentation.menu.binders

import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.OnClick
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.factories.MenuItemsFactory
import com.teachernavigator.teachernavigator.presentation.models.MenuData
import com.teachernavigator.teachernavigator.presentation.models.MenuItem

/**
 * Created by root on 28.08.17.
 */
class BottomBinder(viewGroup: ViewGroup) : BaseMenuBinder(viewInflater(viewGroup, R.layout.v_bottom_biner)) {

    init {
        ButterKnife.bind(this, mView)
    }

    private var mMenuItem: MenuItem? = null

    override fun bind(menuItem: MenuItem) {
        mMenuItem = menuItem
    }

    @OnClick(R.id.v_bottom_binder_btn_add_publication)
    fun addPublication() {
        mMenuItem?.run {
            val action = MenuData<Any>(mType, null)
            mOutputChannel?.onNext(action)

        }
    }

    @OnClick(R.id.v_bottom_holder_tv_about)
    fun about() {
        mMenuItem?.run {
            val action = MenuData<Any>(MenuItemsFactory.MenuItemTypes.ABOUT.id, null)
            mOutputChannel?.onNext(action)

        }
    }

    @OnClick(R.id.v_bottom_holder_tv_tags)
    fun tags() {
        mMenuItem?.run {
            val action = MenuData<Any>(MenuItemsFactory.MenuItemTypes.TAGS.id, null)
            mOutputChannel?.onNext(action)

        }
    }
}