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
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.abstractions.VacanciesView
import com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters.abstractions.IVacanciesPresenter
import kotlinx.android.synthetic.main.fmt_vacancies_list.*
import ru.lliepmah.lib.UniversalAdapter
import javax.inject.Inject

/**
 * Created by lliepmah on 27.09.17
 */
class VacanciesFragment : BaseFragment(), VacanciesView {

    companion object {
        val FRAGMENT_KEY = "jobs_fragment"
    }

    private lateinit var mParentScreenComponent: ParentScreenComponent

    @Inject
    lateinit var vacanciesPresenter: IVacanciesPresenter

    var isSchool = true
    var isCollege = true
    var isUniversity = true

    val adapter: UniversalAdapter by lazy {
        UniversalAdapter(VacancyHolderBuilder(false, null, null, vacanciesPresenter::onResponse))
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fmt_vacancies_list, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        inject()
        vacanciesPresenter.attachView(this)

        vListSwipeLayout.setOnRefreshListener(vacanciesPresenter::refresh)
        vListSwipeLayout.setColorSchemeResources(R.color.colorAccent)
        vListRvData.layoutManager = LinearLayoutManager(context)
        vListRvData.adapter = adapter

        vacanciesChSchool.setOnCheckedChangeListener { _, checked ->
            isSchool = checked
            vacanciesPresenter.setFilter(isSchool, isCollege, isUniversity)
        }
        vacanciesChCollege.setOnCheckedChangeListener { _, checked ->
            isCollege = checked
            vacanciesPresenter.setFilter(isSchool, isCollege, isUniversity)
        }
        vacanciesChUniversity.setOnCheckedChangeListener { _, checked ->
            isUniversity = checked
            vacanciesPresenter.setFilter(isSchool, isCollege, isUniversity)
        }
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