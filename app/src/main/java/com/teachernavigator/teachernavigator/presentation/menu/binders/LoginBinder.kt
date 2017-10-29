package com.teachernavigator.teachernavigator.presentation.menu.binders

import android.view.ViewGroup
import android.widget.ImageView
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.factories.MenuItemsFactory
import com.teachernavigator.teachernavigator.presentation.models.MenuData
import com.teachernavigator.teachernavigator.presentation.models.MenuItem
import com.teachernavigator.teachernavigator.presentation.utils.find

/**
 * Created by root on 28.08.17
 */
class LoginBinder(viewGroup: ViewGroup) : BaseMenuBinder(viewInflater(viewGroup, R.layout.v_login_binder)) {

    private val mIvSettings: ImageView = mView.find(R.id.v_login_binder_iv_settings)
    private val mIvAvatar: ImageView = mView.find(R.id.v_login_binder_iv_avatar)

    override fun bind(menuItem: MenuItem) {
        mView.setOnClickListener {
            val action = MenuData<Any>(menuItem.mType, null)
            mOutputChannel?.onNext(action)
        }

        mIvSettings.setOnClickListener {
            val action = MenuData<Any>(MenuItemsFactory.MenuItemTypes.SETTINGS.id, null)
            mOutputChannel?.onNext(action)
        }

        mIvAvatar.setOnClickListener {
            val action = MenuData<Any>(MenuItemsFactory.MenuItemTypes.LOGIN.id, null)
            mOutputChannel?.onNext(action)
        }
    }
}