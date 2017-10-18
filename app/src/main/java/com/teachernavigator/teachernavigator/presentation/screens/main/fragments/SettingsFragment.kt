package com.teachernavigator.teachernavigator.presentation.screens.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.adapters.ViewPagerAdapter
import com.teachernavigator.teachernavigator.presentation.models.ViewPagerItemContainer
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.SettingsView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.SettingsPresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.ISettingsPresenter
import kotlinx.android.synthetic.main.fmt_settings.*
import javax.inject.Inject

/**
 * Created by root on 18.09.17
 */
class SettingsFragment : BaseFragment(), SettingsView {

    companion object {
        val FRAGMENT_KEY = "settings_fragment"
        val LAST_VIEW_PAGER_POSITION = "last_view_pager_position"
    }

    private lateinit var mAdapter: ViewPagerAdapter
    private var mLastViewPagerPosition = 0

    @Inject
    lateinit var presenter: ISettingsPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater?.inflate(R.layout.fmt_settings, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        mParentScreenComponent.inject(this)
        presenter.attachView(this)

        fmtViewPagerTbTabs.setupWithViewPager(fmtViewPagerVpBody)
        mAdapter = ViewPagerAdapter(childFragmentManager)
        fmtViewPagerVpBody.adapter = mAdapter
        presenter.getSettings()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(LAST_VIEW_PAGER_POSITION, fmtViewPagerVpBody.currentItem)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null)
            mLastViewPagerPosition = savedInstanceState.getInt(LAST_VIEW_PAGER_POSITION, 0)
    }

    override fun loadOrdersFragments(data: List<ViewPagerItemContainer>) {
        mAdapter.loadData(data)
        fmtViewPagerVpBody.currentItem = mLastViewPagerPosition
    }
}