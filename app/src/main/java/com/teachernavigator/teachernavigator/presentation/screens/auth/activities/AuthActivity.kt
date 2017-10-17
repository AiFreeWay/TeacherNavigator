package com.teachernavigator.teachernavigator.presentation.screens.auth.activities

import android.arch.lifecycle.LifecycleRegistry
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.components.DaggerParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.modules.ParentScreenModule
import com.teachernavigator.teachernavigator.application.utils.rootComponent
import com.teachernavigator.teachernavigator.presentation.models.ToolbarStyle
import com.teachernavigator.teachernavigator.presentation.screens.auth.activities.abstractions.AuthParentView
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.abstractions.IAuthParentPresenter
import kotlinx.android.synthetic.main.ac_auth.*
import javax.inject.Inject


/**
 * Created by root on 24.08.17
 */

class AuthActivity : AppCompatActivity(), AuthParentView {

    override fun updateToolbarAlpha(alpha: Float) = Unit
    override fun setToolbarStyle(front: ToolbarStyle) = Unit

    private val mLifecycle: LifecycleRegistry = LifecycleRegistry(this)

    private val mParentScreenComponent by lazy {
        DaggerParentScreenComponent.builder()
                .rootComponent(rootComponent())
                .parentScreenModule(ParentScreenModule(this))
                .build()
    }

    @Inject
    lateinit var mPresenter: IAuthParentPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_auth)
        mParentScreenComponent.inject(this)
        initToolbar()
        mPresenter.attachView(this)
        mPresenter.openStartFragment(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            navigateBack()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getActivity(): AppCompatActivity = this

    override fun getFragmentLayoutId(): Int = R.id.acAuthFlBody

    override fun startProgress() {
        acAuthProgress.visibility = View.VISIBLE
    }

    override fun stopProgress() {
        acAuthProgress.visibility = View.GONE
    }

    override fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun setToolbarTitle(title: Int) {
        supportActionBar?.setTitle(title)
    }

    override fun getLifecycle(): LifecycleRegistry = mLifecycle

    override fun showActionBar() {
        acAuthToolbar.visibility = View.VISIBLE
    }

    override fun hideActionBar() {
        acAuthToolbar.visibility = View.GONE
    }

    override fun navigateBack() {
        mPresenter.navigateBack()
    }

    override fun getContext(): Context = this

    private fun initToolbar() {
        setSupportActionBar(acAuthToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        supportFragmentManager
                .findFragmentById(R.id.acAuthFlBody)
                ?.onActivityResult(requestCode, resultCode, data)
    }
}