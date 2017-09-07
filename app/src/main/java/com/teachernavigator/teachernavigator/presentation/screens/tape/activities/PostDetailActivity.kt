package com.teachernavigator.teachernavigator.presentation.screens.tape.activities

import android.arch.lifecycle.LifecycleRegistry
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
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

/**
 * Created by root on 30.08.17.
 */
class PostDetailActivity: AppCompatActivity(), PostDetailView {

    companion object {
        val POST_ID_KEY: String = "post_id_key_post_detail_activity"
        val POST_TITLE_KEY: String = "post_title_key_post_detail_activity"
    }
    
    @BindView(R.id.ac_post_detail_toolbar)
    lateinit var mToolbar: Toolbar
    @BindView(R.id.ac_post_detail_progress)
    lateinit var mProgressBar: ProgressBar

    @BindView(R.id.ac_post_detail_tv_text)
    lateinit var mTvText: TextView

    private val mLifecycle: LifecycleRegistry = LifecycleRegistry(this)
    private val mPresenter: IPostDetailPresenter = AcPostDetailPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_post_detail)
        ButterKnife.bind(this)
        initToolbar()
        mPresenter.attachView(this)
        mPresenter.putPostId(intent.getIntExtra(POST_ID_KEY, -1))
        setToolbarTitle(intent.getStringExtra(POST_TITLE_KEY))
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

    override fun getFragmentLayoutId(): Int = R.id.ac_main_fl_body

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

    override fun loadPost(post: Post) {
        mTvText.setText(post.text)
    }

    private fun initToolbar() {
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }
}