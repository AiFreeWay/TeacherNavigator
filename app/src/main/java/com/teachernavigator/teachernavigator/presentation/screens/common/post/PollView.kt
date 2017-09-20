package com.teachernavigator.teachernavigator.presentation.screens.common.post

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.models.Choice
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacade
import com.teachernavigator.teachernavigator.presentation.screens.tape.views.ChoiceView
import com.teachernavigator.teachernavigator.presentation.utils.ImageLoader

/**
 * Created by root on 20.09.17.
 */
class PollView : PostView {

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, postControllerFacade: IPostControllerFacade) : super(context, postControllerFacade)

    init {
        mLlMoreAndComplain.visibility = View.GONE
        mIvSubscribe.visibility = View.GONE
        mLlChoices.visibility = View.VISIBLE
    }

    override fun loadData(post: Post) {
        mPost = post
        setClickListeners(post)

        mTvTitle.setText(post.title)
        mTvPostime.setText(post.created)
        mTvText.setText(post.text)

        if (post.author != null) {
            mTvAuthorName.setText(post.author!!.full_name)
            if (post.author != null && post.author!!.avatars != null && post.author!!.avatars.isNotEmpty()) {
                val uri = post.author!!.avatars.get(0).avatar
                ImageLoader.load(getContext(), uri, mIvAvatar)
            }
            mPostControllerFacade.subscribe(post, this)
        } else {
            mIvSubscribe.setOnClickListener { null }
            mTvAuthorName.text = context.getString(R.string.not_define)
        }

        mHvHashTags.setData(post.tags!!)
        mTvLike.setText(post.countLikes.toString())
        mTvDislike.setText(post.countDislikes.toString())
        mTvComments.setText(post.countComments.toString())

        loadLikes(post.vote)
        loadChoices(post.choices!!)
    }

    private fun loadChoices(choices: List<Choice>) {
        mLlChoices.removeAllViews()
        choices.forEach {
            mLlChoices.addView(ChoiceView(context, it))
        }
    }
}