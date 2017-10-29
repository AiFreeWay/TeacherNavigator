package com.teachernavigator.teachernavigator.presentation.menu.binders

import android.support.v4.content.ContextCompat
import android.view.ViewGroup
import android.widget.TextView
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.models.MenuData
import com.teachernavigator.teachernavigator.presentation.models.MenuItem
import com.teachernavigator.teachernavigator.presentation.utils.find

/**
 * Created by root on 28.08.17
 */
class ItemBinder(viewGroup: ViewGroup) : BaseMenuBinder(viewInflater(viewGroup, R.layout.v_item_binder)) {

    val context = viewGroup.context
    val mTvTitle: TextView = mView.find(R.id.v_item_tv_title)

    override fun bind(menuItem: MenuItem) {
        mTvTitle.text = menuItem.mTitle
        val icon = menuItem.mIconRes
        if (icon != null) {
            mTvTitle.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, icon), null, null, null)
        }

        mView.setOnClickListener {
            mOutputChannel?.onNext(MenuData<Any>(menuItem.mType, null))
        }
    }
}