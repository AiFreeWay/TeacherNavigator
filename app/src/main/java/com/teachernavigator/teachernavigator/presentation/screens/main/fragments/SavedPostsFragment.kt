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
import com.teachernavigator.teachernavigator.presentation.adapters.MultyRvAdapter
import com.teachernavigator.teachernavigator.presentation.adapters.holders.SavedPostHolder
import com.teachernavigator.teachernavigator.presentation.screens.base.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.SavedPostsView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.FmtSavedPostsPresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.ISavedPostsPresenter

/**
 * Created by root on 08.09.17.
 */
class SavedPostsFragment : BaseFragment(), SavedPostsView {

    companion object {
        val FRAGMENT_KEY = "saved_posts_fragment"
    }

    @BindView(R.id.fmt_list_rv_list)
    lateinit var mRvList: RecyclerView
    @BindView(R.id.fmt_list_tv_no_data)
    lateinit var mTvNoData: TextView

    private val mPresenter: ISavedPostsPresenter = FmtSavedPostsPresenter()
    private lateinit var mAdapter: MultyRvAdapter<Post>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fmt_list, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        mPresenter.attachView(this)
        mAdapter = MultyRvAdapter(SavedPostHolder(context, mPresenter.getPostControllerFacade()))
        mRvList.layoutManager = LinearLayoutManager(context)
        mRvList.adapter = mAdapter
        mPresenter.getPosts()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.detachView()
    }

    override fun loadPosts(posts: List<Post>) {
        mAdapter.loadData(posts)
    }

    override fun showNoDataText() {
        mTvNoData.visibility = View.VISIBLE
    }

    override fun hideNoDataText() {
        mTvNoData.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.tape_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_search -> { mPresenter.openPostSearchScreen() }
            R.id.action_refresh -> { mPresenter.refresh() }
        }
        return super.onOptionsItemSelected(item)
    }
}