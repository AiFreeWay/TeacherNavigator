package com.teachernavigator.teachernavigator.presentation.screens.tape.activities

import android.arch.lifecycle.LifecycleRegistry
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import com.greenfrvr.hashtagview.HashtagView
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.components.ParentScreenComponent
import com.teachernavigator.teachernavigator.domain.models.Comment
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.screens.tape.activities.absctraction.PostCommentsView
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.AcPostCommentsPresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.abstractions.IPostCommentsPresenter
import com.teachernavigator.teachernavigator.presentation.utils.ImageLoader
import android.support.v4.content.ContextCompat


/**
 * Created by root on 07.09.17.
 */
class PostCommentsActivity: AppCompatActivity(), PostCommentsView {

    companion object {
        val POST_KEY: String = "post_key_post_comments_activity"
    }

    @BindView(R.id.ac_post_comments_iv_avatar)
    lateinit var mIvAvatar: ImageView
    @BindView(R.id.ac_post_comments_iv_subscribe)
    lateinit var mIvSubscribe: ImageView
    @BindView(R.id.ac_post_comments_tv_author_name)
    lateinit var mTvAuthorName: TextView
    @BindView(R.id.ac_post_comments_tv_post_time)
    lateinit var mTvPostime: TextView
    @BindView(R.id.ac_post_comments_tv_text)
    lateinit var mTvText: TextView
    @BindView(R.id.ac_post_comments_tv_complain)
    lateinit var mTvComplain: TextView
    @BindView(R.id.ac_post_comments_tv_like)
    lateinit var mTvLike: TextView
    @BindView(R.id.ac_post_comments_tv_dislike)
    lateinit var mTvDislike: TextView
    @BindView(R.id.ac_post_comments_tv_comments)
    lateinit var mTvComments: TextView
    @BindView(R.id.ac_post_comments_iv_save)
    lateinit var mIvSave: ImageView
    @BindView(R.id.ac_post_comments_hv_hasttags)
    lateinit var mHvHashTags: HashtagView
    
    @BindView(R.id.ac_post_comments_ll_comments)
    lateinit var mLlComments: LinearLayout
    @BindView(R.id.ac_post_comments_sv_comments)
    lateinit var mSvComments: ScrollView

    @BindView(R.id.ac_post_comments_et_comment_text)
    lateinit var mEtCommentText: EditText
    @BindView(R.id.ac_post_comments_iv_do_comment)
    lateinit var mIvDoComment: ImageView
    
    @BindView(R.id.ac_post_comments_toolbar)
    lateinit var mToolbar: Toolbar
    @BindView(R.id.ac_post_comments_progress)
    lateinit var mProgressBar: ProgressBar

    private val mLifecycle: LifecycleRegistry = LifecycleRegistry(this)
    private val mPresenter: IPostCommentsPresenter = AcPostCommentsPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_post_comments)
        ButterKnife.bind(this)
        initToolbar()
        mPresenter.attachView(this)
        loadPost(intent.getSerializableExtra(POST_KEY) as Post)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {
            mPresenter.navigateBack()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getLifecycle(): LifecycleRegistry = mLifecycle

    override fun getActivity(): AppCompatActivity = this

    override fun startProgress() {
        mProgressBar.visibility = View.VISIBLE
    }

    override fun stopProgress() {
        mProgressBar.visibility = View.GONE
    }

    override fun setToolbarTitle(title: String) {
        supportActionBar?.setTitle(title)
    }

    override fun setToolbarTitle(title: Int) {
        supportActionBar?.setTitle(title)
    }

    override fun getContext(): Context = this

    override fun getParentScreenComponent(): ParentScreenComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadComment(comment: Comment) {
        mEtCommentText.setText("")
        val inflater = LayoutInflater.from(this)
        mLlComments.addView(createCommentView(comment, "", inflater))
        mSvComments.post({ mSvComments.fullScroll(ScrollView.FOCUS_DOWN) })
    }

    override fun getFragmentLayoutId(): Int = 0

    override fun loadLikes(vote: Boolean?) {
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

    override fun lockUi() {
        mIvDoComment.isEnabled = false
    }

    override fun unlockUi() {
        mIvDoComment.isEnabled = true
    }

    private fun loadPost(post: Post) {
        setToolbarTitle(post.title!!)
        setClickListeners(post)

        mTvPostime.setText(post.created)
        mTvText.setText(post.text)

        ImageLoader.load(this, "http://mock.com", mIvAvatar)

        mHvHashTags.setData(post.tags!!)
        mTvLike.setText(post.countLikes.toString())
        mTvDislike.setText(post.countDislikes.toString())
        mTvComments.setText(post.countComments.toString())

        loadLikes(post.vote)

        loadComments(post)
        mIvDoComment.setOnClickListener { mPresenter.doComment(post, mEtCommentText.text.toString()) }
    }

    private fun setClickListeners(post: Post) {
        mIvSubscribe.setOnClickListener { mPresenter.getIPostControllerFacade().subscribe(post, mPresenter) }
        mTvComplain.setOnClickListener { mPresenter.getIPostControllerFacade().complain(post, mPresenter) }
        mTvLike.setOnClickListener { mPresenter.getIPostControllerFacade().like(post, mPresenter) }
        mTvDislike.setOnClickListener { mPresenter.getIPostControllerFacade().dislike(post, mPresenter) }
        mIvSave.setOnClickListener { mPresenter.getIPostControllerFacade().save(post, mPresenter) }

        mIvAvatar.setOnClickListener { mPresenter.getIPostControllerFacade().openProfileScreen(post, mPresenter) }
        mTvComments.setOnClickListener { mPresenter.getIPostControllerFacade().openCommentsScreen(post, mPresenter) }
    }
    
    private fun loadComments(post: Post) {
        val inflater = LayoutInflater.from(this)

        if (mLlComments.childCount > 1)
            mLlComments.removeViews(1, mLlComments.childCount)

        post.comments?.forEach {
            mLlComments.addView(createCommentView(it, "", inflater))
        }
    }

    private fun createCommentView(comment: Comment, postAuthorName: String, inflater: LayoutInflater): View {
        val commentView = inflater.inflate(R.layout.v_post_comment, mLlComments, false)
        commentView.findViewById<TextView>(R.id.v_post_comment_tv_text).setText(comment.message)
        commentView.findViewById<TextView>(R.id.v_post_comment_tv_post_author_name).setText(postAuthorName)

        if (comment.user != null) {
            commentView.findViewById<TextView>(R.id.v_post_comment_tv_author_name).setText(comment.user!!.full_name)
            if (comment.user != null && comment.user!!.avatars != null) {
                val avatarImageView = commentView.findViewById<ImageView>(R.id.v_post_comment_iv_avatar)
                ImageLoader.load(this, comment.user!!.avatars!!.avatar, avatarImageView)
            }
        }

        return commentView
    }

    private fun initToolbar() {
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }
}