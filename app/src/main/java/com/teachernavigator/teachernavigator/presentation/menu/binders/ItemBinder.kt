package com.teachernavigator.teachernavigator.presentation.menu.binders

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.models.MenuData
import com.teachernavigator.teachernavigator.presentation.models.MenuItem

/**
 * Created by root on 28.08.17.
 */
class ItemBinder(viewGroup: ViewGroup) : BaseMenuBinder(viewInflater(viewGroup, R.layout.v_item_binder)) {

    @BindView(R.id.v_item_tv_title)
    lateinit var mTvTitle: TextView
    @BindView(R.id.v_item_iv_icon)
    lateinit var mIvIcon: ImageView

    init {
        ButterKnife.bind(this, mView)
    }

    override fun bind(menuItem: MenuItem) {
        mTvTitle.setText(menuItem.mTitle)
        mIvIcon.setImageResource(menuItem.mIconRes!!)
        mView.setOnClickListener {
            val action = MenuData<Any>(menuItem.mType, null)
            mOutputChannel?.onNext(action)
        }
    }
}