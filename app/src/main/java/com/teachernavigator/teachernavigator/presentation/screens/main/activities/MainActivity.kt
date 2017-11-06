package com.teachernavigator.teachernavigator.presentation.screens.main.activities

import android.app.Activity
import android.arch.lifecycle.LifecycleRegistry
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.data.cache.CacheController
import com.teachernavigator.teachernavigator.domain.models.Settings
import com.teachernavigator.teachernavigator.presentation.models.ToolbarStyle
import com.teachernavigator.teachernavigator.presentation.screens.main.activities.abstractions.MainView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.AcMainPresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IMainPresenter
import com.teachernavigator.teachernavigator.presentation.views.MenuArrowDrawable
import kotlinx.android.synthetic.main.ac_main.*


class MainActivity : AppCompatActivity(), MainView {

    private val mLifecycle: LifecycleRegistry = LifecycleRegistry(this)
    private val mPresenter: IMainPresenter = AcMainPresenter()
    private val mMenuArrowDrawable by lazy { MenuArrowDrawable(this) }
    private val mDrawerToggle by lazy {
        ActionBarDrawerToggle(this, acMainDrawer, acMainToolbar, 0, 0).apply {
            drawerArrowDrawable = mMenuArrowDrawable
            acMainToolbar.setNavigationOnClickListener { onMenuClick() }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val settings = CacheController.getData(CacheController.SETTINGS_KEY, Settings()) ?: Settings()
        setTheme(settings.theme)

        setContentView(R.layout.ac_main)
        initToolbar()
        mPresenter.attachView(this)
        mPresenter.loadMenuItemsToViewGroup(acMainLlMenu)
        mPresenter.openStartFragment(savedInstanceState)

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mDrawerToggle.syncState()
    }

    override fun onStart() {
        super.onStart()
        mPresenter.loadProfile()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mPresenter.navigateBack()
            return true
        }
        return false
    }

    override fun getLifecycle(): LifecycleRegistry = mLifecycle

    override fun getActivity(): AppCompatActivity = this

    override fun getFragmentLayoutId(): Int = R.id.acMainFlBody

    override fun startProgress() {
        acMainProgress.visibility = View.VISIBLE
    }

    override fun stopProgress() {
        acMainProgress.visibility = View.GONE
    }

    override fun openSideMenu() = acMainDrawer.openDrawer(Gravity.START)

    override fun closeSideMenu() = acMainDrawer.closeDrawer(Gravity.START)

    override fun setToolbarTitle(title: String) {
        setTitle(title)
    }

    override fun setToolbarTitle(title: Int) {
        setTitle(title)
    }

    override fun getContext(): Context = this

    private fun initToolbar() {
        setSupportActionBar(acMainToolbar)
        /* acMainDrawer.addDrawerListener(mDrawerToggle) */
        supportFragmentManager.addOnBackStackChangedListener { this.onBackStackChanged() }
        onBackStackChanged()
    }

    private fun onMenuClick() {
        if (!isRootFragment) {
            mPresenter.navigateBack()
        } else {
            if (acMainDrawer.isDrawerOpen(Gravity.START))
                acMainDrawer.closeDrawer(Gravity.START)
            else
                acMainDrawer.openDrawer(Gravity.START)
        }
    }

    private val isRootFragment
        get() = supportFragmentManager.backStackEntryCount <= 0

    private fun onBackStackChanged() {
        hideSoftKeyboard()
        updateBackIndicator()
    }

    private fun updateBackIndicator() {
        mMenuArrowDrawable.animateDrawable(!isRootFragment)
    }

    private fun hideSoftKeyboard() {
        if (currentFocus != null) {
            val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }


    override fun setToolbarStyle(style: ToolbarStyle) {

//        if (acMainFlBody != null) {

        val lp = acMainFlBody.layoutParams

        if (lp is RelativeLayout.LayoutParams) {

            if (style == ToolbarStyle.Front) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    lp.removeRule(RelativeLayout.BELOW)
                }
            } else {
                lp.addRule(RelativeLayout.BELOW, R.id.acMainToolbar)
            }
        }
//        }
    }

    override fun updateToolbarAlpha(alpha: Float) {
        acMainToolbar?.background?.alpha = (alpha * 255f).toInt()
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            acMainAppBarLayout?.elevation = (15 * alpha)
//        }
    }

}
