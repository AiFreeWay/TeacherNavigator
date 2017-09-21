package com.teachernavigator.teachernavigator.presentation.adapters

import android.view.ViewGroup
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.adapters.holders.BaseHolder
import com.teachernavigator.teachernavigator.presentation.adapters.holders.PostInOtherProfileHolder
import com.teachernavigator.teachernavigator.presentation.adapters.holders.PostInProfileHolder
import com.teachernavigator.teachernavigator.presentation.adapters.holders.ProfileHeaderHolder
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacade
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IProfileFacade
import com.teachernavigator.teachernavigator.presentation.models.ProfilePostConteainer
import com.teachernavigator.teachernavigator.presentation.screens.common.post.PostShortView

/**
 * Created by root on 20.09.17.
 */
class ProfileAdapterStrategy(private val mFacade: IProfileFacade,
                             private val mIsMyProfile: Boolean, private val mPostControllerFacade:
                             IPostControllerFacade) : AdapterStrategy<ProfilePostConteainer> {

    companion object {

        val TYPE_HEADER = 0
        val TYPE_ITEM = 1
    }

    override fun createHolder(parent: ViewGroup, type: Int): BaseHolder<ProfilePostConteainer> =
            when (type) {
            TYPE_HEADER -> ProfileHeaderHolder(mFacade, mIsMyProfile, parent)
            TYPE_ITEM -> generatePostInProfileHolder(parent)

            else -> throw Exception("Invalid item type $type ProfileAdapterStrategy")
        }

    private fun generatePostInProfileHolder(parent: ViewGroup): BaseHolder<ProfilePostConteainer> {
        if (mIsMyProfile) {
            val view = BaseHolder.viewInflater(parent, R.layout.v_post_in_profile_holder)
            return PostInProfileHolder(view, mPostControllerFacade)
        } else {
            val view = BaseHolder.viewInflater(parent, R.layout.v_post_holder)
            return PostInOtherProfileHolder(view, mPostControllerFacade)
        }
    }
}