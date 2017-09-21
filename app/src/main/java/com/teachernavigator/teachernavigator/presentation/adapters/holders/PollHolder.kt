package com.teachernavigator.teachernavigator.presentation.adapters.holders

import android.content.Context
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacade
import com.teachernavigator.teachernavigator.presentation.screens.common.post.PollView

/**
 * Created by root on 20.09.17.
 */
class PollHolder : BaseHolder<Post> {

    @BindView(R.id.postview) lateinit var mPostView: PollView

    val mPostControllerFacade: IPostControllerFacade

    constructor(context: Context, postController: IPostControllerFacade) : super(context, null) {
        mPostControllerFacade = postController
    }

    constructor(view: View, postController: IPostControllerFacade) : super(view, null) {
        mPostControllerFacade = postController
        ButterKnife.bind(this, itemView)
        mPostView.setPostControllerFacade(mPostControllerFacade)
    }

    override fun create(viewGroup: ViewGroup): BaseHolder<Post> {
        val view = viewInflater(viewGroup, R.layout.v_poll_holder)
        return PollHolder(view, mPostControllerFacade)
    }

    override fun bind(dataModel: Post) {
        mPostView.loadData(dataModel)
    }
}