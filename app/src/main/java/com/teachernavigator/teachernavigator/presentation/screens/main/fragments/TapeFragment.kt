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
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IFmtTapePresenter

/**
 * Created by root on 14.08.17.
 */
class TapeFragment : FragmentChildMainView(), TapeView {

    companion object {
        val FRAGMENT_KEY = "tape_fragment"
    }

    @BindView(R.id.fmt_view_pager_tb_tabs)
    lateinit var mTbTabs: TabLayout
    @BindView(R.id.fmt_view_pager_vp_body)
    lateinit var mVpOrders: ViewPager

    private lateinit var mAdapter: ViewpagerAdapter
    private val mPresenter: IFmtTapePresenter = FmtTapePresenter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fmt_view_pager, container, false)
        ButterKnife.bind(this, view);
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPresenter.attachView(this)
        mTbTabs.setupWithViewPager(mVpOrders)
        mAdapter = ViewpagerAdapter(childFragmentManager)
        mVpOrders.adapter = mAdapter
    }

    override fun onStart() {
        super.onStart()
        getMainView().setToolbarTitle(R.string.tape)
    }

    override fun loadOrdersFragments(data: List<ViewPagerItemContainer>) {
        mAdapter.loadData(data)
    }
}