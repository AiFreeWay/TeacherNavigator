package com.teachernavigator.teachernavigator.presentation.screens.main.fragments

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.adapters.ViewpagerAdapter
import com.teachernavigator.teachernavigator.presentation.models.ViewPagerItemContainer
import com.teachernavigator.teachernavigator.presentation.screens.base.FragmentChildMainView
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.TapeView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.FmtTapePresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.ITapePresenter
import java.util.*

/**
 * Created by root on 14.08.17.
 */
class TapeFragment : FragmentChildMainView(), TapeView {

    companion object {
        val FRAGMENT_KEY = "tape_fragment"
        val LAST_VIEW_PAGER_POSITION = "last_view_pager_position"
    }

    @BindView(R.id.fmt_view_pager_tb_tabs)
    lateinit var mTbTabs: TabLayout
    @BindView(R.id.fmt_view_pager_vp_body)
    lateinit var mVpTapeItems: ViewPager

    private lateinit var mAdapter: ViewpagerAdapter
    private val mPresenter: ITapePresenter = FmtTapePresenter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fmt_view_pager, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPresenter.attachView(this)
        mTbTabs.setupWithViewPager(mVpTapeItems)
        mAdapter = ViewpagerAdapter(childFragmentManager)
        mVpTapeItems.adapter = mAdapter
        mPresenter.loadFragments()
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
            mVpTapeItems.currentItem = savedInstanceState?.getInt(LAST_VIEW_PAGER_POSITION, 0)
    }

    override fun loadOrdersFragments(data: List<ViewPagerItemContainer>) {
        mAdapter.loadData(data)
    }
}