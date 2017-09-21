package com.teachernavigator.teachernavigator.presentation.screens.common.comment

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.models.Comment
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.ICommentControllerFacade
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.ICommentControllerFacadeCallback
import com.teachernavigator.teachernavigator.presentation.utils.ImageLoader
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by root on 13.09.17.
 */
open class CommentView : RelativeLayout, ICommentControllerFacadeCallback {

    @BindView(R.id.v_comment_iv_avatar) lateinit var mIvAvatar: CircleImageView
    @BindView(R.id.v_comment_iv_subscribe) lateinit var mIvSubscribe: ImageView
    @BindView(R.id.v_comment_tv_author_name) lateinit var mTvAuthorName: TextView
    @BindView(R.id.v_comment_tv_post_author_name) lateinit var mTvPostAuthorName: TextView
    @BindView(R.id.v_comment_tv_post_time) lateinit var mTvPostTime: TextView
    @BindView(R.id.v_comment_tv_text) lateinit var mTvText: TextView
    @BindView(R.id.v_comment_ll_open_branch) lateinit var mLlOpenBranch: LinearLayout
    @BindView(R.id.v_comment_btn_open_branch) lateinit var mBtnOpenBranch: Button

    private lateinit var mCommentControllerFacade: ICommentControllerFacade

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, commentControllerFacade: ICommentControllerFacade) : super(context) {
        mCommentControllerFacade = commentControllerFacade
    }

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.v_comment, this)
        ButterKnife.bind(this, view)
    }

    fun setPostControllerFacade(commentControllerFacade: ICommentControllerFacade) {
        mCommentControllerFacade = commentControllerFacade
    }

    fun loadData(comment: Comment) {
        mTvPostTime.setText("")
        mTvText.setText(comment.message)

        if (comment.user != null) {
            if (!TextUtils.isEmpty(comment.user!!.full_name))
                mTvAuthorName.setText(comment.user!!.full_name)
            else
                mTvAuthorName.text = context.getString(R.string.not_define)

            if (comment.user!!.avatars != null)
                ImageLoader.load(context, comment.user!!.avatars!!.avatar, mIvAvatar)
        } else
            mTvAuthorName.text = context.getString(R.string.not_define)

        if (comment.author != null)
            mTvPostAuthorName.setText(comment.author!!.full_name)
        else
            mTvPostAuthorName.text = context.getString(R.string.not_define)

        setClickListeners(comment)
    }

    override fun onSubscribe() {}

    override fun onError(error: Throwable) {
        showToast(R.string.error_throwed)
    }

    protected fun showToast(strRest: Int) {
        Toast.makeText(getContext(), getContext().getString(strRest), Toast.LENGTH_SHORT).show()
    }

    protected fun setClickListeners(comment: Comment) {
        mIvAvatar.setOnClickListener { mCommentControllerFacade.openProfileScreen(comment, this) }
        mIvSubscribe.setOnClickListener { mCommentControllerFacade.subscribe(comment, this) }
        mBtnOpenBranch.setOnClickListener { mCommentControllerFacade.openBranch(comment, this) }
    }
}