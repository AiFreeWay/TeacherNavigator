package com.teachernavigator.teachernavigator.presentation.adapters.holders

import android.content.Context
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.models.Comment
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacade
import com.teachernavigator.teachernavigator.presentation.screens.common.comment.MyCommentView

/**
 * Created by root on 08.09.17.
 */
class  MyCommentHolder: BaseHolder<Comment> {

    @BindView(R.id.v_my_post_holder_comment_comment) lateinit var mCommentView: MyCommentView

    val mPostControllerFacade: IPostControllerFacade

    constructor(context: Context, postController: IPostControllerFacade) : super(context, null) {
        mPostControllerFacade = postController
    }

    constructor(view: View, postController: IPostControllerFacade) : super(view, null) {
        mPostControllerFacade = postController
        ButterKnife.bind(this, itemView)
        mCommentView.setPostControllerFacade(mPostControllerFacade)
    }

    override fun create(viewGroup: ViewGroup): BaseHolder<Comment> {
        val view = viewInflater(viewGroup, R.layout.v_my_comment_holder)
        return MyCommentHolder(view, mPostControllerFacade)
    }

    override fun bind(dataModel: Comment) {
        mCommentView.loadData(dataModel)
    }
}