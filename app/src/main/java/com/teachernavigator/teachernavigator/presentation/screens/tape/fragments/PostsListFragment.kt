package com.teachernavigator.teachernavigator.presentation.screens.tape.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.adapters.MultyRvAdapter
import com.teachernavigator.teachernavigator.presentation.adapters.holders.PostHolder
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.tape.fragments.abstractions.PostsListView
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.FmtPostsListPresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.abstractions.IPostsListPresenter

/**
 * Created by root on 22.08.17.
 */
class PostsListFragment : BaseFragment(), PostsListView {

    companion object {
        val POSTS_TYPE_KEY = "tape_type_key"
    }

    @BindView(R.id.fmt_list_rv_list) lateinit var mRvList: RecyclerView
    @BindView(R.id.fmt_list_tv_no_data) lateinit var mTvNoData: TextView

    private val mPresenter: IPostsListPresenter = FmtPostsListPresenter()
    private lateinit var mAdapter: MultyRvAdapter<Post>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fmt_list, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPresenter.setTapeType(arguments.getInt(POSTS_TYPE_KEY))
        mPresenter.attachView(this)
        mAdapter = MultyRvAdapter(PostHolder(context, mPresenter.getPostControllerFacade()))
        mRvList.layoutManager = LinearLayoutManager(context)
        mRvList.adapter = mAdapter
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

    override fun refresh() {
        mPresenter.getPosts()
    }
}