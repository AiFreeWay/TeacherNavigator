package com.teachernavigator.teachernavigator.presentation.screens.tape.activities

import android.arch.lifecycle.LifecycleRegistry
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import butterknife.BindView
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.components.ParentScreenComponent
import com.teachernavigator.teachernavigator.presentation.screens.tape.activities.absctraction.PostSearchView
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.AcPostSearchPresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.abstractions.IPostSearchPresenter

/**
 * Created by root on 06.09.17.
 */
class PostSearchActivity: AppCompatActivity(), PostSearchView {

    @BindView(R.id.ac_post_search_toolbar)
    lateinit var mToolbar: Toolbar
    @BindView(R.id.ac_post_search_progress)
    lateinit var mProgressBar: ProgressBar
    @BindView(R.id.ac_post_search_btn_find)
    lateinit var mBtnFind: Button

    private val mLifecycle: LifecycleRegistry = LifecycleRegistry(this)
    private val mPresenter: IPostSearchPresenter = AcPostSearchPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_post_search)
        ButterKnife.bind(this)
        initToolbar()
        mPresenter.attachView(this)
        mBtnFind.setOnClickListener { mPresenter.navigateBack() }
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

    override fun getFragmentLayoutId(): Int = 0

    private fun initToolbar() {
        setSupportActionBar(mToolbar)
        supportActionBar?.title = getString(R.string.search_publication)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }
}