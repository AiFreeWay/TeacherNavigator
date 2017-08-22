package com.teachernavigator.teachernavigator.presentation.adapters.holders.menu_holders

import android.view.ViewGroup
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.adapters.holders.BaseHolder
import com.teachernavigator.teachernavigator.presentation.models.MenuData
import com.teachernavigator.teachernavigator.presentation.models.MenuItem

/**
 * Created by root on 16.08.17.
 */
class LoginHolder(viewGroup: ViewGroup) : BaseMenuHolder(viewInflater(viewGroup, R.layout.v_login_holder)) {

    override fun create(viewGroup: ViewGroup): BaseHolder<MenuItem> {
        return LoginHolder(viewGroup)
    }

    override fun bind(dataModel: MenuItem) {
        itemView.setOnClickListener {
            val action = MenuData<Any>(dataModel.mType, null)
            mObserverEmitFromHolder?.onNext(action)
        }
    }
}