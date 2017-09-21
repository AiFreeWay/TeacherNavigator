package com.teachernavigator.teachernavigator.presentation.adapters.holders

import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacade
import com.teachernavigator.teachernavigator.presentation.models.ProfilePostConteainer
import com.teachernavigator.teachernavigator.presentation.screens.common.post.PostView

/**
 * Created by root on 20.09.17.
 */
class PostInOtherProfileHolder : BaseHolder<ProfilePostConteainer> {

    @BindView(R.id.v_post_holder_detail_postview) lateinit var mPostView: PostView

    val mPostControllerFacade: IPostControllerFacade

    constructor(view: View, postController: IPostControllerFacade) : super(view, null) {
        mPostControllerFacade = postController
        ButterKnife.bind(this, itemView)
        mPostView.setPostControllerFacade(mPostControllerFacade)
    }

    override fun create(viewGroup: ViewGroup): BaseHolder<ProfilePostConteainer> {
        val view = viewInflater(viewGroup, R.layout.v_post_holder)
        return PostInOtherProfileHolder(view, mPostControllerFacade)
    }

    override fun bind(dataModel: ProfilePostConteainer) {
        mPostView.loadData(dataModel.post!!)
    }
}