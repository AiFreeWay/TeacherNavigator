package com.teachernavigator.teachernavigator.presentation.adapters.holders

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.greenfrvr.hashtagview.HashtagView
import com.squareup.picasso.Picasso
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacade
import com.teachernavigator.teachernavigator.presentation.utils.CircleTransform

/**
 * Created by root on 18.08.17.
 */
class PostHolder: BaseHolder<Post> {

    @BindView(R.id.v_post_holder_iv_avatar)
    lateinit var mIvAvatar: ImageView
    @BindView(R.id.v_post_holder_iv_subscribe)
    lateinit var mIvSubscribe: ImageView
    @BindView(R.id.v_post_holder_tv_title)
    lateinit var mTvTitle: TextView
    @BindView(R.id.v_post_holder_tv_author_name)
    lateinit var mTvAuthorName: TextView
    @BindView(R.id.v_post_holder_tv_post_time)
    lateinit var mTvPostime: TextView
    @BindView(R.id.v_post_holder_tv_text)
    lateinit var mTvText: TextView
    @BindView(R.id.v_post_holder_btn_more)
    lateinit var mBtnMore: Button
    @BindView(R.id.v_post_holder_tv_complain)
    lateinit var mTvComplain: TextView
    @BindView(R.id.v_post_holder_tv_like)
    lateinit var mTvLike: TextView
    @BindView(R.id.v_post_holder_tv_dislike)
    lateinit var mTvDislike: TextView
    @BindView(R.id.v_post_holder_tv_comments)
    lateinit var mTvComments: TextView
    @BindView(R.id.v_post_holder_iv_save)
    lateinit var mIvSave: ImageView
    @BindView(R.id.v_post_holder_hv_hasttags)
    lateinit var mHvHashTags: HashtagView

    val mPostControllerFacade: IPostControllerFacade

    constructor(context: Context, postController: IPostControllerFacade) : super(context, null) {
        mPostControllerFacade = postController
    }

    constructor(view: View, postController: IPostControllerFacade) : super(view, null) {
        mPostControllerFacade = postController
        ButterKnife.bind(this, itemView)
    }

    override fun create(viewGroup: ViewGroup): BaseHolder<Post> {
        val view = viewInflater(viewGroup, R.layout.v_post_holder)
        return PostHolder(view, mPostControllerFacade)
    }

    override fun bind(dataModel: Post) {
        setClickListeners(dataModel)

        mTvTitle.setText(dataModel.title)
        mTvPostime.setText(dataModel.created)
        mTvText.setText(dataModel.text)

        Picasso.with(itemView.context)
                .load("http://mock.com")
                .placeholder(R.drawable.ic_avatar)
                .error(R.drawable.ic_avatar)
                .into(mIvAvatar)

        mHvHashTags.setData(dataModel.tags!!)
        mTvLike.setText(dataModel.countLikes.toString())
        mTvDislike.setText(dataModel.countDislikes.toString())
        mTvComments.setText(dataModel.countComments.toString())
    }

    private fun setClickListeners(dataModel: Post) {
        mIvSubscribe.setOnClickListener { subscribe(dataModel) }
        mTvComplain.setOnClickListener { complain(dataModel) }
        mTvLike.setOnClickListener { like(dataModel) }
        mTvDislike.setOnClickListener { dislike(dataModel) }
        mIvSave.setOnClickListener { save(dataModel) }

        mIvAvatar.setOnClickListener { mPostControllerFacade.openProfileScreen(dataModel) }
        mBtnMore.setOnClickListener { mPostControllerFacade.openPostDetailScreen(dataModel) }
        mTvComments.setOnClickListener { mPostControllerFacade.openCommentsScreen(dataModel) }
    }

    private fun subscribe(dataModel: Post) {
        mPostControllerFacade.subscribe(dataModel)
    }

    private fun complain(dataModel: Post) {
        mPostControllerFacade.complain(dataModel)
    }

    private fun like(dataModel: Post) {
        mPostControllerFacade.like(dataModel)
    }

    private fun dislike(dataModel: Post) {
        mPostControllerFacade.dislike(dataModel)
    }

    private fun save(dataModel: Post) {
        mPostControllerFacade.save(dataModel)
    }
}