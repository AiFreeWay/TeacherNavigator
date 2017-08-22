package com.teachernavigator.teachernavigator.presentation.screens.tape.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.adapters.MultyRvAdapter
import com.teachernavigator.teachernavigator.presentation.adapters.holders.PostHolder
import com.teachernavigator.teachernavigator.presentation.screens.base.FragmentChildMainView
import com.teachernavigator.teachernavigator.presentation.screens.tape.fragments.abstractions.TapeListView
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.FmtTapeListPresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.abstractions.ITapeListPresenter

/**
 * Created by root on 22.08.17.
 */
class TapeListFragment : FragmentChildMainView(), TapeListView {

    companion object {
        val TAPE_TYPE_KEY = "tape_type_key"
    }

    @BindView(R.id.fmt_list_rv_list)
    lateinit var mRvList: RecyclerView

    private val mPresenter: ITapeListPresenter = FmtTapeListPresenter()
    private lateinit var mAdapter: MultyRvAdapter<Post>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fmt_list, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPresenter.setTapeType(arguments.getInt(TAPE_TYPE_KEY))
        mPresenter.attachView(this)
        mAdapter = MultyRvAdapter<Post>(PostHolder(context, mPresenter::onPostClick))
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
}