package com.teachernavigator.teachernavigator.presentation.screens.common.post

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.models.Post

/**
 * Created by root on 21.09.17.
 */
class PostPreview : PostView {

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        mIvSubscribe.visibility = View.GONE
        mLlMoreAndComplain.visibility = View.GONE
        mTvLike.visibility = View.GONE
        mTvDislike.visibility = View.GONE
        mTvComments.visibility = View.GONE
        mIvSave.visibility = View.GONE
    }

    override fun loadData(post: Post) {
        mTvTitle.setText(post.title)
        mTvAuthorName.setText(R.string.you)
        mTvText.setText(post.text)
        mTvPostime.setText(R.string.will_set_current_time)
    }
}