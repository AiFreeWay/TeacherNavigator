package com.teachernavigator.teachernavigator.presentation.screens.tape.activities

import android.arch.lifecycle.LifecycleRegistry
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.greenfrvr.hashtagview.HashtagView
import com.squareup.picasso.Picasso
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.components.ParentScreenComponent
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.screens.tape.activities.absctraction.PostDetailView
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.AcPostDetailPresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.abstractions.IPostDetailPresenter
import com.teachernavigator.teachernavigator.presentation.utils.ImageLoader

/**
 * Created by root on 30.08.17.
 */
class PostDetailActivity: AppCompatActivity(), PostDetailView {

    companion object {
        val POST_KEY: String = "post_key_post_detail_activity"
    }

    @BindView(R.id.ac_post_detail_iv_avatar)
    lateinit var mIvAvatar: ImageView
    @BindView(R.id.ac_post_detail_iv_subscribe)
    lateinit var mIvSubscribe: ImageView
    @BindView(R.id.ac_post_detail_tv_author_name)
    lateinit var mTvAuthorName: TextView
    @BindView(R.id.ac_post_detail_tv_post_time)
    lateinit var mTvPostime: TextView
    @BindView(R.id.ac_post_detail_tv_text)
    lateinit var mTvText: TextView
    @BindView(R.id.ac_post_detail_tv_complain)
    lateinit var mTvComplain: TextView
    @BindView(R.id.ac_post_detail_tv_like)
    lateinit var mTvLike: TextView
    @BindView(R.id.ac_post_detail_tv_dislike)
    lateinit var mTvDislike: TextView
    @BindView(R.id.ac_post_detail_tv_comments)
    lateinit var mTvComments: TextView
    @BindView(R.id.ac_post_detail_iv_save)
    lateinit var mIvSave: ImageView
    @BindView(R.id.ac_post_detail_hv_hasttags)
    lateinit var mHvHashTags: HashtagView
    
    @BindView(R.id.ac_post_detail_toolbar)
    lateinit var mToolbar: Toolbar
    @BindView(R.id.ac_post_detail_progress)
    lateinit var mProgressBar: ProgressBar

    private val mLifecycle: LifecycleRegistry = LifecycleRegistry(this)
    private val mPresenter: IPostDetailPresenter = AcPostDetailPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_post_detail)
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

    override fun getFragmentLayoutId(): Int = 0

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

    private fun initToolbar() {
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }
}