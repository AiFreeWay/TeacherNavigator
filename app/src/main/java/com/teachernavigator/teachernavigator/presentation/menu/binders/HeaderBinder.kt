package com.teachernavigator.teachernavigator.presentation.menu.binders

import android.view.ViewGroup
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.factories.MenuItemsFactory
import com.teachernavigator.teachernavigator.presentation.models.MenuData
import com.teachernavigator.teachernavigator.presentation.models.MenuItem

/**
 * Created by root on 28.08.17.
 */
class HeaderBinder(viewGroup: ViewGroup) : BaseMenuBinder(viewInflater(viewGroup, R.layout.v_header_binder)) {

    @BindView(R.id.v_header_binder_iv_settings)
    lateinit var mIvSettings: ImageView
    @BindView(R.id.v_header_binder_iv_avatar)
    lateinit var mIvAvatar: ImageView

    init {
        ButterKnife.bind(this, mView)
    }

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
            val action = MenuData<Any>(MenuItemsFactory.MenuItemTypes.PROFILE_HEADER.id, null)
            mOutputChannel?.onNext(action)
        }
    }
}