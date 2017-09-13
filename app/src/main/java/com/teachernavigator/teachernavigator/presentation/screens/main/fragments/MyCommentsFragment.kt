package com.teachernavigator.teachernavigator.presentation.screens.main.fragments

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
import com.teachernavigator.teachernavigator.domain.models.Comment
import com.teachernavigator.teachernavigator.presentation.adapters.MultyRvAdapter
import com.teachernavigator.teachernavigator.presentation.adapters.holders.MyCommentHolder
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.MyCommentsView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.FmtMyCommentsPresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IMyCommentsPresenter

/**
 * Created by root on 08.09.17.
 */
class MyCommentsFragment : BaseFragment(), MyCommentsView {

    companion object {
        val FRAGMENT_KEY = "my_comments_fragment"
    }

    @BindView(R.id.fmt_list_rv_list) lateinit var mRvList: RecyclerView
    @BindView(R.id.fmt_list_tv_no_data) lateinit var mTvNoData: TextView

    private val mPresenter: IMyCommentsPresenter = FmtMyCommentsPresenter()
    private lateinit var mAdapter: MultyRvAdapter<Comment>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fmt_list, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPresenter.attachView(this)
        mAdapter = MultyRvAdapter(MyCommentHolder(context, mPresenter.getIPostControllerFacade()))
        mRvList.layoutManager = LinearLayoutManager(context)
        mRvList.adapter = mAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.detachView()
    }

    override fun loadComments(posts: List<Comment>) {
        mAdapter.loadData(posts)
    }

    override fun showNoDataText() {
        mTvNoData.visibility = View.VISIBLE
    }

    override fun hideNoDataText() {
        mTvNoData.visibility = View.GONE
    }
}