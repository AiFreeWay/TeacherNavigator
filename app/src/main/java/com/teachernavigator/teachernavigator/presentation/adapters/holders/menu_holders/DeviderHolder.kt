package com.teachernavigator.teachernavigator.presentation.adapters.holders.menu_holders

import android.view.ViewGroup
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.models.MenuItem

/**
 * Created by root on 16.08.17.
 */
class DeviderHolder: BaseMenuHolder {

    constructor(viewGroup: ViewGroup) : super(viewInflater(viewGroup, R.layout.v_devider_holder)) {}

    override fun create(viewGroup: ViewGroup): BaseMenuHolder {
        return DeviderHolder(viewGroup)
    }


    override fun bind(dataModel: MenuItem) {}
}