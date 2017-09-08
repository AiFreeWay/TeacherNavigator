package com.teachernavigator.teachernavigator.presentation.adapters.holders

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
 * Created by root on 08.09.17.
 */
class SavedPostHolder: BaseHolder<Post>, IPostControllerFacadeCallback {

    @BindView(R.id.v_saved_post_holder_iv_avatar)
    lateinit var mIvAvatar: ImageView
    @BindView(R.id.v_saved_post_holder_tv_title)
    lateinit var mTvTitle: TextView
    @BindView(R.id.v_saved_post_holder_tv_author_name)
    lateinit var mTvAuthorName: TextView
    @BindView(R.id.v_saved_post_holder_tv_post_time)
    lateinit var mTvPostime: TextView
    @BindView(R.id.v_saved_post_holder_tv_text)
    lateinit var mTvText: TextView
    @BindView(R.id.v_saved_post_holder_btn_more)
    lateinit var mBtnMore: Button
    @BindView(R.id.v_saved_post_holder_tv_like)
    lateinit var mTvLike: TextView
    @BindView(R.id.v_saved_post_holder_tv_dislike)
    lateinit var mTvDislike: TextView
    @BindView(R.id.v_saved_post_holder_tv_comments)
    lateinit var mTvComments: TextView
    @BindView(R.id.v_saved_post_holder_hv_hasttags)
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
        val view = viewInflater(viewGroup, R.layout.v_saved_post_holder)
        return SavedPostHolder(view, mPostControllerFacade)
    }

    override fun bind(dataModel: Post) {
        setClickListeners(dataModel)

        mTvTitle.setText(dataModel.title)
        mTvPostime.setText(dataModel.created)
        mTvText.setText(dataModel.text)

        ImageLoader.load(getContext(), "http://mock.com", mIvAvatar)

        mHvHashTags.setData(dataModel.tags!!)
        mTvLike.setText(dataModel.countLikes.toString())
        mTvDislike.setText(dataModel.countDislikes.toString())
        mTvComments.setText(dataModel.countComments.toString())

        loadLikes(dataModel.vote)
    }

    private fun setClickListeners(dataModel: Post) {
        mTvLike.setOnClickListener { mPostControllerFacade.like(dataModel, this) }
        mTvDislike.setOnClickListener { mPostControllerFacade.dislike(dataModel, this) }

        mIvAvatar.setOnClickListener { mPostControllerFacade.openProfileScreen(dataModel, this) }
        mBtnMore.setOnClickListener { mPostControllerFacade.openPostDetailScreen(dataModel) }
        mTvComments.setOnClickListener { mPostControllerFacade.openCommentsScreen(dataModel, this) }
    }

    private fun loadLikes(vote: Boolean?) {
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

    override fun onLike(result: Monade) {
        if (!result.isError)
            loadLikes(true)
    }

    override fun onDislike(result: Monade) {
        if (!result.isError)
            loadLikes(false)
    }

    override fun onSave(result: Monade) {
        if (!result.isError)
            showToast(R.string.added)
    }

    override fun onSubscribe(result: Monade) {

    }

    override fun onComplain(result: Monade) {

    }

    override fun onError(error: Throwable) {
        showToast(R.string.error_throwed)
    }

    private fun showToast(strRest: Int) {
        Toast.makeText(getContext(), getContext().getString(strRest), Toast.LENGTH_SHORT).show()
    }

    private fun getContext() = itemView.context
}