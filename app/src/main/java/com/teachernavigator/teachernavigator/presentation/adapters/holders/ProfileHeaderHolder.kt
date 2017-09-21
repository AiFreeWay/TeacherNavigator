package com.teachernavigator.teachernavigator.presentation.adapters.holders

import android.view.ViewGroup
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IProfileFacade
import com.teachernavigator.teachernavigator.presentation.models.ProfilePostConteainer
import com.teachernavigator.teachernavigator.presentation.screens.main.views.ProfileHeaderView

/**
 * Created by root on 20.09.17.
 */
class ProfileHeaderHolder(facade: IProfileFacade, isMyProfile: Boolean, viewGroup: ViewGroup) :
        BaseHolder<ProfilePostConteainer>(ProfileHeaderView(facade, isMyProfile, viewGroup.context), null) {

    override fun create(viewGroup: ViewGroup): BaseHolder<ProfilePostConteainer> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun bind(dataModel: ProfilePostConteainer) {
        (itemView as ProfileHeaderView).loadData(dataModel.profile!!)
    }

    init {
        ButterKnife.bind(this, itemView)
    }
}