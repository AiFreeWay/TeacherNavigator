package com.teachernavigator.teachernavigator.presentation.screens.main.fragments

import android.os.Bundle
import android.view.*
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.adapters.ViewPagerAdapter
import com.teachernavigator.teachernavigator.presentation.models.ViewPagerItemContainer
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.TapeView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.ITapePresenter
import com.teachernavigator.teachernavigator.presentation.utils.argNullable
import kotlinx.android.synthetic.main.fmt_feed.*
import javax.inject.Inject


/**
 * Created by root on 14.08.17
 */
class TapeFragment : BaseFragment(), TapeView {

    companion object {
        val FRAGMENT_KEY = "tape_fragment"

        val POSTS_SOURCE_KEY = "posts_source"
    }

    private val mAdapter by lazy { ViewPagerAdapter(childFragmentManager) }

    @Inject
    lateinit var mPresenter: ITapePresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater?.inflate(R.layout.fmt_feed, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mParentScreenComponent.inject(this)

        setHasOptionsMenu(true)
        mPresenter.attachView(this)

        fmtFeedTbTabs.setupWithViewPager(fmtFeedVpBody)
        fmtFeedVpBody.adapter = mAdapter
        mPresenter.loadFragments(argNullable(POSTS_SOURCE_KEY))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.detachView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                mPresenter.openPostSearchScreen()
            }
            R.id.action_refresh -> {
                mPresenter.refresh(childFragmentManager.findFragmentById(fmtFeedVpBody.id))
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
    }
}