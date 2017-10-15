package com.teachernavigator.teachernavigator.presentation.screens.main.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.models.PostModel
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.MyPublicationsView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.MyPublicationsPresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IMyPublicationsPresenter

/**
 * Created by root on 13.09.17
 */
class MyPublicationsFragment : BaseFragment(), MyPublicationsView {

    companion object {
        val FRAGMENT_KEY = "my_publications_fragment"
    }

    @BindView(R.id.fmt_list_rv_list) lateinit var mRvList: RecyclerView
    @BindView(R.id.fmt_list_tv_no_data) lateinit var mTvNoData: TextView

    private val mPresenter: IMyPublicationsPresenter = MyPublicationsPresenter()
    private lateinit var mAdapter: Nothing

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fmt_list, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        mPresenter.attachView(this)
        mAdapter = TODO()
        mRvList.layoutManager = LinearLayoutManager(context)
        mRvList.adapter = mAdapter
        mPresenter.getMyPublications()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.detachView()
    }

    override fun loadMyPublications(posts: List<PostModel>) {
        // mAdapter.loadData(posts)
    }

    override fun showNoDataText() {
        mTvNoData.visibility = View.VISIBLE
    }

    override fun hideNoDataText() {
        mTvNoData.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.refresh_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_refresh -> {
                mPresenter.refresh()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}