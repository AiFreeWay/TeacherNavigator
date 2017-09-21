package com.teachernavigator.teachernavigator.presentation.screens.main.activities

import android.arch.lifecycle.LifecycleRegistry
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import butterknife.BindView
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseView
import com.teachernavigator.teachernavigator.presentation.screens.common.post.PostView

/**
 * Created by root on 21.09.17.
 */
class PreviewPostActivity : AppCompatActivity(), BaseView {

    companion object {
        val POST_KEY: String = "post_key_post_preview_activity"
    }

    @BindView(R.id.postview) lateinit var mPostview: PostView

    @BindView(R.id.ac_preview_post_toolbar) lateinit var mToolbar: Toolbar
    @BindView(R.id.ac_preview_post_progress) lateinit var mProgressBar: ProgressBar

    private val mLifecycle: LifecycleRegistry = LifecycleRegistry(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_preview_post)
        ButterKnife.bind(this)
        initToolbar()
        val post = intent.getSerializableExtra(POST_KEY) as Post
        mPostview.loadData(post)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getLifecycle(): LifecycleRegistry = mLifecycle

    override fun getContext(): Context = this
    

    private fun initToolbar() {
        setSupportActionBar(mToolbar)
        supportActionBar?.setTitle(R.string.preview)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }
}