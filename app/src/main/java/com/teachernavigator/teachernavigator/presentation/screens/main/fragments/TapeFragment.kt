package com.teachernavigator.teachernavigator.presentation.screens.main.fragments

import android.os.Bundle
import android.view.*
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.components.DaggerParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.components.ParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.modules.ParentScreenModule
import com.teachernavigator.teachernavigator.application.utils.rootComponent
import com.teachernavigator.teachernavigator.presentation.adapters.ViewPagerAdapter
import com.teachernavigator.teachernavigator.presentation.models.ViewPagerItemContainer
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.TapeView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.ITapePresenter
import kotlinx.android.synthetic.main.fmt_feed.*
import javax.inject.Inject


/**
 * Created by root on 14.08.17
 */
class TapeFragment : BaseFragment(), TapeView {

    companion object {
        val FRAGMENT_KEY = "tape_fragment"
        val LAST_VIEW_PAGER_POSITION = "last_view_pager_position"
    }

    private lateinit var mParentScreenComponent: ParentScreenComponent

    private val mAdapter by lazy { ViewPagerAdapter(childFragmentManager) }

    private var mLastViewPagerPosition = 0

    @Inject
    lateinit var mPresenter: ITapePresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater?.inflate(R.layout.fmt_feed, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        inject()
        setHasOptionsMenu(true)
        mPresenter.attachView(this)

        fmtFeedTbTabs.setupWithViewPager(fmtFeedVpBody)
        fmtFeedVpBody.adapter = mAdapter
        mPresenter.loadFragments()
    }

    private fun inject() {
        mParentScreenComponent = DaggerParentScreenComponent.builder()
                .rootComponent(rootComponent())
                .parentScreenModule(ParentScreenModule(getParentView()))
                .build()
                .also { it.inject(this) }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.detachView()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(LAST_VIEW_PAGER_POSITION, fmtFeedVpBody.currentItem)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null)
            mLastViewPagerPosition = savedInstanceState.getInt(LAST_VIEW_PAGER_POSITION, 0)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                mPresenter.openPostSearchScreen()
            }
            R.id.action_refresh -> {
                mPresenter.refresh(mAdapter.currentFragment(fmtFeedVpBody.currentItem))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_refresh_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun loadOrdersFragments(data: List<ViewPagerItemContainer>) {
        mAdapter.loadData(data)
        fmtFeedVpBody.currentItem = mLastViewPagerPosition
    }
}