package com.teachernavigator.teachernavigator.presentation.adapters.holders

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.models.Comment
import com.teachernavigator.teachernavigator.presentation.utils.ImageLoader

/**
 * Created by root on 08.09.17.
 */
class  MyCommentHolder: BaseHolder<Comment> {

    @BindView(R.id.v_my_comment_iv_avatar)
    lateinit var mIvAvatar: ImageView
    @BindView(R.id.v_my_comment_iv_subscribe)
    lateinit var mIvSubscribe: ImageView
    @BindView(R.id.v_my_comment_tv_author_name)
    lateinit var mTvAuthorName: TextView
    @BindView(R.id.v_my_comment_tv_post_time)
    lateinit var mTvPostTime: TextView
    @BindView(R.id.v_my_comment_tv_text)
    lateinit var mTvText: TextView
    @BindView(R.id.v_my_comment_holder_btn_open_branch)
    lateinit var mBtnOpenBranch: Button

    constructor(context: Context) : super(context, null)

    constructor(view: View) : super(view, null) {
        ButterKnife.bind(this, itemView)
    }

    override fun create(viewGroup: ViewGroup): BaseHolder<Comment> {
        val view = viewInflater(viewGroup, R.layout.v_my_comment_holder)
        return MyCommentHolder(view)
    }

    override fun bind(dataModel: Comment) {
        mTvPostTime.setText("")
        mTvText.setText(dataModel.message)

        if (dataModel.user != null) {
            mTvAuthorName.setText(dataModel.user!!.full_name)
            if (dataModel.user!!.avatars != null)
                ImageLoader.load(itemView.context, dataModel.user!!.avatars!!.avatar, mIvAvatar)
        }
    }
}