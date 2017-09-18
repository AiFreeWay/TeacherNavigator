package com.teachernavigator.teachernavigator.presentation.screens.main.fragments

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.*
import butterknife.BindView
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.adapters.ViewPagerAdapter
import com.teachernavigator.teachernavigator.presentation.models.ViewPagerItemContainer
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.SettingsView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.FmtSettingsPresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.ISettingsPresenter

/**
 * Created by root on 18.09.17.
 */
class SettingsFragment : BaseFragment(), SettingsView {

    companion object {
        val FRAGMENT_KEY = "settings_fragment"
        val LAST_VIEW_PAGER_POSITION = "last_view_pager_position"
    }

    @BindView(R.id.fmt_view_pager_tb_tabs) lateinit var mTbTabs: TabLayout
    @BindView(R.id.fmt_view_pager_vp_body) lateinit var mVpTapeItems: ViewPager

    private lateinit var mAdapter: ViewPagerAdapter
    private val mPresenter: ISettingsPresenter = FmtSettingsPresenter()
    private var mLastViewPagerPosition = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fmt_view_pager, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        mPresenter.attachView(this)
        mTbTabs.setupWithViewPager(mVpTapeItems)
        mAdapter = ViewPagerAdapter(childFragmentManager)
        mVpTapeItems.adapter = mAdapter
        mPresenter.getSettings()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.detachView()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(LAST_VIEW_PAGER_POSITION, mVpTapeItems.currentItem)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null)
            mLastViewPagerPosition = savedInstanceState.getInt(LAST_VIEW_PAGER_POSITION, 0)
    }

    override fun loadOrdersFragments(data: List<ViewPagerItemContainer>) {
        mAdapter.loadData(data)
        mVpTapeItems.currentItem = mLastViewPagerPosition
    }
}