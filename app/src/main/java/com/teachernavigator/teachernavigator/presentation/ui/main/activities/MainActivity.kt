package com.teachernavigator.teachernavigator.presentation.ui.main.activities

import android.arch.lifecycle.LifecycleRegistry
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import butterknife.BindView
import butterknife.ButterKnife

import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.ui.main.activities.abstractions.MainView
import com.teachernavigator.teachernavigator.presentation.ui.main.presenters.AcMainPresenter

class MainActivity : AppCompatActivity(), MainView {

    @BindView(R.id.ac_main_toolbar)
    lateinit var mToolbar: Toolbar
    @BindView(R.id.ac_main_drawer)
    lateinit var mDrawer: DrawerLayout
    @BindView(R.id.ac_main_fl_body)
    lateinit var mFlBody: FrameLayout
    @BindView(R.id.ac_main_progress)
    lateinit var mProgressBar: ProgressBar

    private val mLifecycle: LifecycleRegistry = LifecycleRegistry(this)
    private lateinit var mPresenter: AcMainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_main)
        ButterKnife.bind(this)
        mPresenter = AcMainPresenter(this)
        initToolbar()
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

    override fun openSideMenu() = mDrawer.openDrawer(Gravity.START)

    override fun closeSideMenu() = mDrawer.closeDrawer(Gravity.START)

    override fun setToolbarTitle(title: String) {
        mToolbar.setTitle(title)
    }

    private fun initToolbar() {
        val drawerToggle = object : ActionBarDrawerToggle(this, mDrawer, mToolbar, 0, 0) {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, 0F)
            }
        }
        drawerToggle.syncState()
    }
}
