package com.teachernavigator.teachernavigator.presentation.adapters.holders

import android.content.Context
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.models.Post

/**
 * Created by root on 18.08.17.
 */
class PostHolder: BaseHolder<Post> {

    constructor(context: Context, onClick: (Post) -> Unit) : super(context, onClick) {
    }

    constructor(view: View, onClick: (Post) -> Unit) : super(view, onClick) {
        ButterKnife.bind(this, itemView)
    }

    override fun create(viewGroup: ViewGroup): BaseHolder<Post> {
        val view = viewInflater(viewGroup, R.layout.v_post_holder)
        return PostHolder(view, mOnClick!!)
    }

    override fun bind(dataModel: Post) {
        itemView.setOnClickListener { mOnClick?.invoke(dataModel) }
    }
}