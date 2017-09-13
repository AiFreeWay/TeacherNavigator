package com.teachernavigator.teachernavigator.presentation.screens.common.comment

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.models.Comment
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacade
import com.teachernavigator.teachernavigator.presentation.utils.ImageLoader

/**
 * Created by root on 13.09.17.
 */
open class CommentView : RelativeLayout {

    @BindView(R.id.v_comment_iv_avatar) lateinit var mIvAvatar: ImageView
    @BindView(R.id.v_comment_iv_subscribe) lateinit var mIvSubscribe: ImageView
    @BindView(R.id.v_comment_tv_author_name) lateinit var mTvAuthorName: TextView
    @BindView(R.id.v_comment_tv_post_author_name) lateinit var mTvPostAuthorName: TextView
    @BindView(R.id.v_comment_tv_post_time) lateinit var mTvPostTime: TextView
    @BindView(R.id.v_comment_tv_text) lateinit var mTvText: TextView
    @BindView(R.id.v_comment_ll_open_branch) lateinit var mLlOpenBranch: LinearLayout
    @BindView(R.id.v_comment_btn_open_branch) lateinit var mBtnOpenBranch: Button

    private lateinit var mPostControllerFacade: IPostControllerFacade

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, postControllerFacade: IPostControllerFacade) : super(context) {
        mPostControllerFacade = postControllerFacade
    }

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.v_comment, this)
        ButterKnife.bind(this, view)
    }

    fun setPostControllerFacade(postControllerFacade: IPostControllerFacade) {
        mPostControllerFacade = postControllerFacade
    }

    fun loadData(comment: Comment) {
        mTvPostTime.setText("")
        mTvPostAuthorName.setText("")
        mTvText.setText(comment.message)

        if (comment.user != null) {
            mTvAuthorName.setText(comment.user!!.full_name)
            if (comment.user!!.avatars != null)
                ImageLoader.load(context, comment.user!!.avatars!!.avatar, mIvAvatar)
        }
    }
}