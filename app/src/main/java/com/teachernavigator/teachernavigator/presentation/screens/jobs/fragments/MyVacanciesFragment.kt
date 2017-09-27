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
import com.teachernavigator.teachernavigator.presentation.adapters.holders.VacancyHolderBuilder
import com.teachernavigator.teachernavigator.presentation.models.VacancyModel
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.abstractions.MyJobsView
import com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters.abstractions.IMyVacanciesPresenter
import kotlinx.android.synthetic.main.fmt_my_vacancies.*
import ru.lliepmah.lib.UniversalAdapter
import javax.inject.Inject

/**
 * Created by lliepmah on 24.09.17
 */
class MyVacanciesFragment : BaseFragment(), MyJobsView {

    companion object {
        val FRAGMENT_KEY = "my_jobs_fragment"
    }

    private lateinit var mParentScreenComponent: ParentScreenComponent

    @Inject
    lateinit var myVacanciesPresenter: IMyVacanciesPresenter

    val adapter: UniversalAdapter by lazy {
        UniversalAdapter(VacancyHolderBuilder(true, myVacanciesPresenter::onProlong, myVacanciesPresenter::onDelete, null))
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fmt_my_vacancies, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        inject()
        myVacanciesPresenter.attachView(this)

        vListSwipeLayout.setOnRefreshListener(myVacanciesPresenter::refresh)
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

    override fun setJobs(jobsList: List<VacancyModel>) {
        adapter.clear()
        adapter.addAll(jobsList)
        adapter.notifyDataSetChanged()
    }

    override fun showRefresh() {
        vListSwipeLayout?.isRefreshing = true
    }

    override fun hideRefresh() {
        vListSwipeLayout?.isRefreshing = false
    }

}