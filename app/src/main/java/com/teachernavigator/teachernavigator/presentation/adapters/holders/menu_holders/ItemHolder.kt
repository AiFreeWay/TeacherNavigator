package com.teachernavigator.teachernavigator.presentation.adapters.holders.menu_holders

import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.adapters.holders.BaseHolder
import com.teachernavigator.teachernavigator.presentation.models.MenuData
import com.teachernavigator.teachernavigator.presentation.models.MenuItem

/**
 * Created by root on 16.08.17.
 */
class ItemHolder: BaseMenuHolder {

    @BindView(R.id.v_menu_item_tv_title)
    lateinit var mTvTitle: TextView

    constructor(viewGroup: ViewGroup) : super(viewInflater(viewGroup, R.layout.v_menu_item_holder)) {
        ButterKnife.bind(this, itemView)
    }

    override fun create(viewGroup: ViewGroup): BaseHolder<MenuItem> {
        return LoginHolder(viewGroup)
    }

    override fun bind(dataModel: MenuItem) {
        mTvTitle.setText(dataModel.mTitle)
        itemView.setOnClickListener {
            val action = MenuData<Any>(dataModel.mType, null)
            mObserverEmitFromHolder?.onNext(action)
        }
    }
}