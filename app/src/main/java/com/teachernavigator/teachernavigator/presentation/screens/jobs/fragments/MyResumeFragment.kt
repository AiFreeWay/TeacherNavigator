package com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.adapters.holders.ResumeHolderBuilder
import com.teachernavigator.teachernavigator.presentation.models.ResumeModel
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.abstractions.MyResumeView
import com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters.abstractions.IMyResumePresenter
import kotlinx.android.synthetic.main.fmt_my_resume.*
import ru.lliepmah.lib.UniversalAdapter
import javax.inject.Inject

/**
 * Created by lliepmah on 24.09.17
 */
class MyResumeFragment : BaseFragment(), MyResumeView {

    companion object {
        val FRAGMENT_KEY = "my_resume_fragment"
    }

    @Inject
    lateinit var myResumePresenter: IMyResumePresenter

    val adapter: UniversalAdapter by lazy {
        UniversalAdapter(ResumeHolderBuilder(true, myResumePresenter::onProlong, myResumePresenter::onDelete, myResumePresenter::openResume))
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fmt_my_resume, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mParentScreenComponent.inject(this)
        myResumePresenter.attachView(this)

        myResumeBtnCreate.setOnClickListener { myResumePresenter.createResume() }
        vListSwipeLayout.setOnRefreshListener(myResumePresenter::refresh)
        vListSwipeLayout.setColorSchemeResources(R.color.colorAccent)
        vListRvData.layoutManager = LinearLayoutManager(context)
        vListRvData.adapter = adapter
    }

    override fun setResumes(listOfResume: List<ResumeModel>) {
        adapter.clear()
        adapter.addAll(listOfResume)
        adapter.notifyDataSetChanged()
    }

    override fun showRefresh() {
        vListSwipeLayout?.isRefreshing = true
    }

    override fun hideRefresh() {
        vListSwipeLayout?.isRefreshing = false
    }

}