package com.teachernavigator.teachernavigator.presentation.screens.auth.activities

import android.arch.lifecycle.LifecycleRegistry
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import butterknife.BindView
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.components.ParentScreenComponent
import com.teachernavigator.teachernavigator.presentation.screens.auth.activities.abstractions.AuthParentView
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.AcAuthParentPresenter
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.abstractions.IAuthParentPresenter

/**
 * Created by root on 24.08.17.
 */
class AuthActivity : AppCompatActivity(), AuthParentView {

    @BindView(R.id.ac_auth_toolbar)
    lateinit var mToolbar: Toolbar
    @BindView(R.id.ac_auth_progress)
    lateinit var mProgressBar: ProgressBar

    private val mLifecycle: LifecycleRegistry = LifecycleRegistry(this)
    private val mPresenter: IAuthParentPresenter = AcAuthParentPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_auth)
        ButterKnife.bind(this)
        initToolbar()
        mPresenter.attachView(this)
        mPresenter.openStartFragment(savedInstanceState)
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

    override fun getActivity(): AppCompatActivity = this

    override fun getFragmentLayoutId(): Int = R.id.ac_auth_fl_body

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

    override fun getParentScreenComponent(): ParentScreenComponent = mPresenter.getParentScreenComponent()

    override fun getLifecycle(): LifecycleRegistry = mLifecycle

    override fun showActionBar() {
        mToolbar.visibility = View.VISIBLE
    }

    override fun hightActionBar() {
        mToolbar.visibility = View.GONE
    }

    private fun initToolbar() {
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }
}