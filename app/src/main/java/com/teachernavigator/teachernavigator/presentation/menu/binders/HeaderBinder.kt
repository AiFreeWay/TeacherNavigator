package com.teachernavigator.teachernavigator.presentation.menu.binders

import android.text.TextUtils
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.models.Profile
import com.teachernavigator.teachernavigator.presentation.factories.MenuItemsFactory
import com.teachernavigator.teachernavigator.presentation.models.MenuData
import com.teachernavigator.teachernavigator.presentation.models.MenuItem
import com.teachernavigator.teachernavigator.presentation.utils.ImageLoader
import com.teachernavigator.teachernavigator.presentation.utils.find

/**
 * Created by root on 28.08.17
 */
class HeaderBinder(viewGroup: ViewGroup) : BaseMenuBinder(viewInflater(viewGroup, R.layout.v_header_binder)) {

    private val mIvSettings: ImageView = mView.find(R.id.v_header_binder_iv_settings)
    private val mIvAvatar: ImageView = mView.find(R.id.v_header_binder_iv_avatar)
    private val mTvTitle: TextView = mView.find(R.id.v_header_binder_tv_title)
    private val mTvRating: TextView = mView.find(R.id.v_header_binder_tv_rating)
    private val mTvSubscribers: TextView = mView.find(R.id.v_header_binder_tv_subscribers)

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

        mInputChannel!!.subscribe({
            if (it.mType == MenuItemsFactory.MenuItemTypes.PROFILE_HEADER.id)
                loadProfile(it.mData as Profile)
        })
    }

    private fun loadProfile(profile: Profile) {
        if (!profile.avatar.isNullOrBlank())
            ImageLoader.load(mView.context, profile.avatar, mIvAvatar)

        if (!TextUtils.isEmpty(profile.full_name))
            mTvTitle.text = profile.full_name
        else
            mTvTitle.text = mView.context.getString(R.string.not_define)

        val subscribers = if (profile.count_subscribers != null && profile.count_subscribers > 0)
            profile.count_subscribers
        else 0

        mTvSubscribers.text = subscribers.toString()
    }
}