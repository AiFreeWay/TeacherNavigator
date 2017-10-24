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
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.abstractions.ResumeListView
import com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters.abstractions.IResumeListPresetner
import kotlinx.android.synthetic.main.fmt_resume_list.*
import ru.lliepmah.lib.UniversalAdapter
import javax.inject.Inject

/**
 * Created by lliepmah on 24.09.17
 */
class ResumeListFragment : BaseFragment(), ResumeListView {

    companion object {
        val FRAGMENT_KEY = "resume_list_fragment"
    }

    @Inject
    lateinit var resumeListPresetner: IResumeListPresetner

    val adapter: UniversalAdapter by lazy {
        UniversalAdapter(ResumeHolderBuilder(false, null, null, resumeListPresetner::openResume))
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fmt_resume_list, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mParentScreenComponent.inject(this)
        resumeListPresetner.attachView(this)

        vListSwipeLayout.setOnRefreshListener(resumeListPresetner::refresh)
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