package com.teachernavigator.teachernavigator.presentation.screens.common.post

import android.content.Context
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import com.greenfrvr.hashtagview.HashtagView
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacade
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacadeCallback
import com.teachernavigator.teachernavigator.presentation.utils.ImageLoader
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by root on 12.09.17.
 */
open class PostView : RelativeLayout, IPostControllerFacadeCallback {

    @BindView(R.id.v_post_iv_avatar) lateinit var mIvAvatar: CircleImageView
    @BindView(R.id.v_post_iv_subscribe) lateinit var mIvSubscribe: ImageView
    @BindView(R.id.v_post_tv_title) lateinit var mTvTitle: TextView
    @BindView(R.id.v_post_tv_author_name) lateinit var mTvAuthorName: TextView
    @BindView(R.id.v_post_tv_post_time) lateinit var mTvPostime: TextView
    @BindView(R.id.v_post_tv_text) lateinit var mTvText: TextView
    @BindView(R.id.v_post_ll_more_and_complain) lateinit var mLlMoreAndComplain: LinearLayout
    @BindView(R.id.v_post_ll_choices) lateinit var mLlChoices: LinearLayout
    @BindView(R.id.v_post_btn_more) lateinit var mBtnMore: Button
    @BindView(R.id.v_post_tv_complain) lateinit var mTvComplain: TextView
    @BindView(R.id.v_post_tv_like) lateinit var mTvLike: TextView
    @BindView(R.id.v_post_tv_dislike) lateinit var mTvDislike: TextView
    @BindView(R.id.v_post_tv_comments) lateinit var mTvComments: TextView
    @BindView(R.id.v_post_iv_save) lateinit var mIvSave: ImageView
    @BindView(R.id.v_post_hv_hasttags) lateinit var mHvHashTags: HashtagView

    protected var mPostControllerFacade: IPostControllerFacade? = null
    protected lateinit var mPost: Post

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

    open fun loadData(post: Post) {
        mPost = post
        setClickListeners(post)

        mTvTitle.text = post.title
        mTvPostime.text = post.created
        mTvText.text = post.text

        if (post.author != null) {
            if (TextUtils.isEmpty(post.author!!.full_name))
                mTvAuthorName.text = context.getString(R.string.not_define)
            else
                mTvAuthorName.text = post.author?.full_name ?: ""

            if (post.author != null && post.author!!.avatars != null && post.author!!.avatars.isNotEmpty()) {
                val uri = post.author!!.avatars.get(0).avatar
                ImageLoader.load(getContext(), uri, mIvAvatar)
            }

            if (post.author!!.id == null)
                mIvSubscribe.setOnClickListener { }
            else
                mIvSubscribe.setOnClickListener { mPostControllerFacade?.subscribe(post, this) }

        } else {
            mIvSubscribe.visibility = GONE
            mTvAuthorName.text = context.getString(R.string.not_define)
        }

        mHvHashTags.setData(post.tags!!)
        mTvLike.setText(post.countLikes.toString())
        mTvDislike.setText(post.countDislikes.toString())
        mTvComments.setText(post.countComments.toString())

        loadLikes(post.vote)
    }

    fun loadLikes(vote: Boolean?) {
        if (vote == true) {
            val like = ContextCompat.getDrawable(getContext(), R.drawable.ic_like_active)
            mTvLike.setCompoundDrawablesWithIntrinsicBounds(like, null, null, null)
            val dislike = ContextCompat.getDrawable(getContext(), R.drawable.ic_dislike)
            mTvDislike.setCompoundDrawablesWithIntrinsicBounds(dislike, null, null, null)

            mTvDislike.text.toString().toInt()
        } else if (vote == false) {
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
        changeLikesDislikesCount(true)
        loadLikes(true)
        mPost.vote = true
    }

    override fun onDislike() {
        changeLikesDislikesCount(false)
        loadLikes(false)
        mPost.vote = false
    }

    override fun onSave() {
        showToast(R.string.do_saved)
    }

    override fun onSubscribe() {
        showToast(R.string.subscribed)
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
        mTvComplain.setOnClickListener { mPostControllerFacade?.complain(dataModel, this) }
        mTvLike.setOnClickListener { mPostControllerFacade?.like(true, dataModel, this) }
        mTvDislike.setOnClickListener { mPostControllerFacade?.like(false, dataModel, this) }
        mIvSave.setOnClickListener { mPostControllerFacade?.save(dataModel, this) }

        mIvAvatar.setOnClickListener { mPostControllerFacade?.openProfileScreen(dataModel, this) }
        mBtnMore.setOnClickListener { mPostControllerFacade?.openPostDetailScreen(dataModel) }
        mTvComments.setOnClickListener { mPostControllerFacade?.openCommentsScreen(dataModel, this) }
    }

    protected fun changeLikesDislikesCount(isLike: Boolean) {
        if (mPost.vote == null) {
            if (isLike)
                incrementLikes()
            else
                incrementDislikes()
            return
        }

        if (isLike && mPost.vote != true) {
            incrementLikes()
            decrementDislikes()
        } else if (!isLike && mPost.vote == true)  {
            incrementDislikes()
            decrementLikes()
        }
    }

    protected fun incrementLikes() {
        var likes = mPost.countLikes
        likes = likes!!+1
        mPost.countLikes = likes
        mTvLike.setText(mPost.countLikes.toString())
    }

    protected fun decrementLikes() {
        var likes = mPost.countLikes
        likes = likes!!-1
        mPost.countLikes = likes
        mTvLike.setText(mPost.countLikes.toString())
    }

    protected fun incrementDislikes() {
        var dislikes = mPost.countDislikes
        dislikes = dislikes!!+1
        mPost.countDislikes = dislikes
        mTvDislike.setText(mPost.countDislikes.toString())
    }

    protected fun decrementDislikes() {
        var dislikes = mPost.countDislikes
        dislikes = dislikes!!-1
        mPost.countDislikes = dislikes
        mTvDislike.setText(mPost.countDislikes.toString())
    }
}