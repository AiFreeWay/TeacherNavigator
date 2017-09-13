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
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.components.ParentScreenComponent
import com.teachernavigator.teachernavigator.domain.models.Comment
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.screens.common.comment.CommentView
import com.teachernavigator.teachernavigator.presentation.screens.tape.activities.absctraction.PostCommentsView
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.AcPostCommentsPresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.abstractions.IPostCommentsPresenter
import com.teachernavigator.teachernavigator.presentation.utils.ImageLoader
import com.teachernavigator.teachernavigator.presentation.screens.common.post.PostView


/**
 * Created by root on 07.09.17.
 */
class PostCommentsActivity: AppCompatActivity(), PostCommentsView {

    companion object {
        val POST_KEY: String = "post_key_post_comments_activity"
    }

    @BindView(R.id.ac_post_comments_postview) lateinit var mPostview: PostView
    
    @BindView(R.id.ac_post_comments_ll_comments) lateinit var mLlComments: LinearLayout
    @BindView(R.id.ac_post_comments_sv_comments) lateinit var mSvComments: ScrollView

    @BindView(R.id.ac_post_comments_et_comment_text) lateinit var mEtCommentText: EditText
    @BindView(R.id.ac_post_comments_iv_do_comment) lateinit var mIvDoComment: ImageView
    
    @BindView(R.id.ac_post_comments_toolbar) lateinit var mToolbar: Toolbar
    @BindView(R.id.ac_post_comments_progress) lateinit var mProgressBar: ProgressBar

    private val mLifecycle: LifecycleRegistry = LifecycleRegistry(this)
    private val mPresenter: IPostCommentsPresenter = AcPostCommentsPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_post_comments)
        ButterKnife.bind(this)
        initToolbar()
        mPresenter.attachView(this)
        mPostview.setPostControllerFacade(mPresenter.getIPostControllerFacade())
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
        mLlComments.addView(createCommentView(comment, inflater))
        mSvComments.post({ mSvComments.fullScroll(ScrollView.FOCUS_DOWN) })
    }

    override fun getFragmentLayoutId(): Int = 0

    override fun lockUi() {
        mIvDoComment.isEnabled = false
    }

    override fun unlockUi() {
        mIvDoComment.isEnabled = true
    }

    private fun loadPost(post: Post) {
        setToolbarTitle(post.title!!)
        mPostview.loadData(post)
        loadComments(post)
        mIvDoComment.setOnClickListener { mPresenter.doComment(post, mEtCommentText.text.toString()) }
    }
    
    private fun loadComments(post: Post) {
        val inflater = LayoutInflater.from(this)

        if (mLlComments.childCount > 1)
            mLlComments.removeViews(1, mLlComments.childCount)

        post.comments?.forEach {
            mLlComments.addView(createCommentView(it, inflater))
        }
    }

    private fun createCommentView(comment: Comment, inflater: LayoutInflater): View {
        val commentView = inflater.inflate(R.layout.v_post_comment, mLlComments, false)
        commentView.findViewById<CommentView>(R.id.v_post_comment_comment).loadData(comment)
        return commentView
    }

    private fun initToolbar() {
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }
}