package com.teachernavigator.teachernavigator.presentation.screens.common.post

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import com.greenfrvr.hashtagview.HashtagView
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.models.Monade
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacade
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacadeCallback
import com.teachernavigator.teachernavigator.presentation.utils.ImageLoader

/**
 * Created by root on 12.09.17.
 */
open class PostView : RelativeLayout, IPostControllerFacadeCallback {

    @BindView(R.id.v_post_iv_avatar) lateinit var mIvAvatar: ImageView
    @BindView(R.id.v_post_iv_subscribe) lateinit var mIvSubscribe: ImageView
    @BindView(R.id.v_post_tv_title) lateinit var mTvTitle: TextView
    @BindView(R.id.v_post_tv_author_name) lateinit var mTvAuthorName: TextView
    @BindView(R.id.v_post_tv_post_time) lateinit var mTvPostime: TextView
    @BindView(R.id.v_post_tv_text) lateinit var mTvText: TextView
    @BindView(R.id.v_post_btn_more) lateinit var mBtnMore: Button
    @BindView(R.id.v_post_tv_complain) lateinit var mTvComplain: TextView
    @BindView(R.id.v_post_tv_like) lateinit var mTvLike: TextView
    @BindView(R.id.v_post_tv_dislike) lateinit var mTvDislike: TextView
    @BindView(R.id.v_post_tv_comments) lateinit var mTvComments: TextView
    @BindView(R.id.v_post_iv_save) lateinit var mIvSave: ImageView
    @BindView(R.id.v_post_hv_hasttags) lateinit var mHvHashTags: HashtagView

    private lateinit var mPostControllerFacade: IPostControllerFacade

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, postControllerFacade: IPostControllerFacade) : super(context) {
        mPostControllerFacade = postControllerFacade
    }

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.v_post, this)
        ButterKnife.bind(this, view)
    }

    fun setPostControllerFacade(postControllerFacade: IPostControllerFacade) {
        mPostControllerFacade = postControllerFacade
    }

    fun loadData(post: Post) {
        setClickListeners(post)

        mTvTitle.setText(post.title)
        mTvPostime.setText(post.created)
        mTvText.setText(post.text)

        ImageLoader.load(getContext(), "http://mock.com", mIvAvatar)

        mHvHashTags.setData(post.tags!!)
        mTvLike.setText(post.countLikes.toString())
        mTvDislike.setText(post.countDislikes.toString())
        mTvComments.setText(post.countComments.toString())

        loadLikes(post.vote)
    }

    fun loadLikes(vote: Boolean?) {
        if (vote != null && vote) {
            val like = ContextCompat.getDrawable(getContext(), R.drawable.ic_like_active)
            mTvLike.setCompoundDrawablesWithIntrinsicBounds(like, null, null, null)
            val dislike = ContextCompat.getDrawable(getContext(), R.drawable.ic_dislike)
            mTvDislike.setCompoundDrawablesWithIntrinsicBounds(dislike, null, null, null)
        } else if (vote != null && !vote) {
            val like = ContextCompat.getDrawable(getContext(), R.drawable.ic_like)
            mTvLike.setCompoundDrawablesWithIntrinsicBounds(like, null, null, null)
            val dislike = ContextCompat.getDrawable(getContext(), R.drawable.ic_dislike_active)
            mTvDislike.setCompoundDrawablesWithIntrinsicBounds(dislike, null, null, null)
        } else {
            val like = ContextCompat.getDrawable(getContext(), R.drawable.ic_like)
            mTvLike.setCompoundDrawablesWithIntrinsicBounds(like, null, null, null)
            val dislike = ContextCompat.getDrawable(getContext(), R.drawable.ic_dislike)
            mTvDislike.setCompoundDrawablesWithIntrinsicBounds(dislike, null, null, null)
        }
    }

    override fun onLike() {
    }

    override fun onDislike() {
    }

    override fun onSave() {
    }

    override fun onSubscribe() {

    }

    override fun onComplain() {

    }

    override fun onError(error: Throwable) {
        showToast(R.string.error_throwed)
    }

    protected fun showToast(strRest: Int) {
        Toast.makeText(getContext(), getContext().getString(strRest), Toast.LENGTH_SHORT).show()
    }

    protected fun setClickListeners(dataModel: Post) {
        mIvSubscribe.setOnClickListener { mPostControllerFacade.subscribe(dataModel, this) }
        mTvComplain.setOnClickListener { mPostControllerFacade.complain(dataModel, this) }
        mTvLike.setOnClickListener { mPostControllerFacade.like(dataModel, this) }
        mTvDislike.setOnClickListener { mPostControllerFacade.dislike(dataModel, this) }
        mIvSave.setOnClickListener { mPostControllerFacade.save(dataModel, this) }

        mIvAvatar.setOnClickListener { mPostControllerFacade.openProfileScreen(dataModel, this) }
        mBtnMore.setOnClickListener { mPostControllerFacade.openPostDetailScreen(dataModel) }
        mTvComments.setOnClickListener { mPostControllerFacade.openCommentsScreen(dataModel, this) }
    }
}