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
class BottomHolder(viewGroup: ViewGroup) : BaseMenuHolder(viewInflater(viewGroup, R.layout.v_bottom_holder)) {

    @BindView(R.id.v_bottom_holder_tv_title)
    lateinit var mTvTitle: TextView

    init {
        ButterKnife.bind(this, itemView)
    }

    override fun create(viewGroup: ViewGroup): BaseHolder<MenuItem> {
        return LoginHolder(viewGroup)
    }

    override fun bind(dataModel: MenuItem) {
        mTvTitle.setText(dataModel.mTitle)
        itemView.setOnClickListener {
            val action = MenuData<Any>(dataModel.mType, null)
            mObserverEmitInPresenter?.onNext(action)
        }
    }
}