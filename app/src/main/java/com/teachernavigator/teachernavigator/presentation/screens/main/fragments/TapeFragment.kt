package com.teachernavigator.teachernavigator.presentation.screens.main.fragments

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.*
import butterknife.BindView
import butterknife.ButterKnife
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.adapters.ViewpagerAdapter
import com.teachernavigator.teachernavigator.presentation.models.ViewPagerItemContainer
import com.teachernavigator.teachernavigator.presentation.screens.base.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.TapeView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.FmtTapePresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.ITapePresenter


/**
 * Created by root on 14.08.17.
 */
class TapeFragment : BaseFragment(), TapeView {

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
            mLastViewPagerPosition = savedInstanceState.getInt(LAST_VIEW_PAGER_POSITION, 0)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_search -> { mPresenter.openPostSearchScreen() }
            R.id.action_refresh -> { mPresenter.refresh(mAdapter.currentFragment(mVpTapeItems.currentItem)) }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.tape_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun loadOrdersFragments(data: List<ViewPagerItemContainer>) {
        mAdapter.loadData(data)
        mVpTapeItems.currentItem = mLastViewPagerPosition
    }
}