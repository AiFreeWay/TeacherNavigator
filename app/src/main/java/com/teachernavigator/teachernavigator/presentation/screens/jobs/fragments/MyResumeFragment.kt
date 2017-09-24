package com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.components.DaggerParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.components.ParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.modules.ParentScreenModule
import com.teachernavigator.teachernavigator.application.utils.rootComponent
import com.teachernavigator.teachernavigator.presentation.adapters.holders.ResumeHolderBuilder
import com.teachernavigator.teachernavigator.presentation.models.ResumeModel
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.abstractions.MyResumeView
import com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters.abstractions.IMyResumePresenter
import kotlinx.android.synthetic.main.fmt_vacancies.*
import ru.lliepmah.lib.UniversalAdapter
import javax.inject.Inject

/**
 * Created by lliepmah on 24.09.17
 */
class MyResumeFragment : BaseFragment(), MyResumeView {

    companion object {
        val FRAGMENT_KEY = "my_resume_fragment"
    }

    private lateinit var mParentScreenComponent: ParentScreenComponent

    @Inject
    lateinit var myResumePresenter: IMyResumePresenter

    val adapter: UniversalAdapter by lazy {
        UniversalAdapter(ResumeHolderBuilder(myResumePresenter::onProlong, myResumePresenter::onDelete))
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fmt_my_resume, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        inject()
        myResumePresenter.attachView(this)

        vListSwipeLayout.setOnRefreshListener(myResumePresenter::refresh)
        vListSwipeLayout.setColorSchemeResources(R.color.colorAccent)
        vListRvData.layoutManager = LinearLayoutManager(context)
        vListRvData.adapter = adapter
    }

    private fun inject() {
        mParentScreenComponent = DaggerParentScreenComponent.builder()
                .rootComponent(rootComponent())
                .parentScreenModule(ParentScreenModule(getParentView()))
                .build()
                .also { it.inject(this) }
    }

    override fun setResumes(listOfResume: List<ResumeModel>) {
        adapter.addAll(listOfResume)
        adapter.notifyDataSetChanged()
    }

//    override fun setResume(jobsList: List<VacancyModel>) {
//        adapter.addAll(jobsList)
//        adapter.notifyDataSetChanged()
//    }

    override fun showRefresh() {
        vListSwipeLayout?.isRefreshing = true
    }

    override fun hideRefresh() {
        vListSwipeLayout?.isRefreshing = false
    }

}