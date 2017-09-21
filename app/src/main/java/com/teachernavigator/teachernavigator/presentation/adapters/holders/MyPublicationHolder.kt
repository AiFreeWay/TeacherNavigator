package com.teachernavigator.teachernavigator.presentation.adapters.holders

import android.content.Context
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacade
import com.teachernavigator.teachernavigator.presentation.screens.common.post.PostView

/**
 * Created by root on 13.09.17.
 */
class MyPublicationHolder: BaseHolder<Post> {

    @BindView(R.id.v_my_publication_holder_detail_postview) lateinit var mPostView: PostView

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
        val view = viewInflater(viewGroup, R.layout.v_my_publication_holder)
        return MyPublicationHolder(view, mPostControllerFacade)
    }

    override fun bind(dataModel: Post) {
        mPostView.loadData(dataModel)
    }
}