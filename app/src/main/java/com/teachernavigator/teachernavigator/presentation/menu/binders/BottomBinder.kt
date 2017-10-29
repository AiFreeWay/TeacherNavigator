package com.teachernavigator.teachernavigator.presentation.menu.binders

import android.support.v7.widget.AppCompatButton
import android.view.ViewGroup
import android.widget.TextView
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.factories.MenuItemsFactory
import com.teachernavigator.teachernavigator.presentation.models.MenuData
import com.teachernavigator.teachernavigator.presentation.models.MenuItem

/**
 * Created by root on 28.08.17
 */
class BottomBinder(viewGroup: ViewGroup) : BaseMenuBinder(viewInflater(viewGroup, R.layout.v_bottom_biner)) {

    private val btnAddPublication: AppCompatButton = mView.findViewById(R.id.v_bottom_binder_btn_add_publication)
    private val tvAbout: TextView = mView.findViewById(R.id.v_bottom_holder_tv_about)
    private val tvTags: TextView = mView.findViewById(R.id.v_bottom_holder_tv_tags)

    init {
        btnAddPublication.setOnClickListener { addPublication() }
        tvAbout.setOnClickListener { about() }
        tvTags.setOnClickListener { tags() }
    }

    private var mMenuItem: MenuItem? = null

    override fun bind(menuItem: MenuItem) {
        mMenuItem = menuItem
    }

    fun addPublication() {
        mMenuItem?.run {
            mOutputChannel?.onNext(MenuData<Any>(mType, null))
        }
    }

    fun about() {
        mMenuItem?.run {
            mOutputChannel?.onNext(MenuData<Any>(MenuItemsFactory.MenuItemTypes.ABOUT.id, null))
        }
    }

    fun tags() {
        mMenuItem?.run {
            mOutputChannel?.onNext(MenuData<Any>(MenuItemsFactory.MenuItemTypes.TAGS.id, null))

        }
    }
}