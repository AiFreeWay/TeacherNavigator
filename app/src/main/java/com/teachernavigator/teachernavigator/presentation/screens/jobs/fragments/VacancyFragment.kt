package com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.components.DaggerParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.components.ParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.modules.ParentScreenModule
import com.teachernavigator.teachernavigator.application.utils.rootComponent
import com.teachernavigator.teachernavigator.presentation.adapters.holders.ResponseHolderBuilder
import com.teachernavigator.teachernavigator.presentation.adapters.holders.VacancyHolder
import com.teachernavigator.teachernavigator.presentation.models.VacancyModel
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.abstractions.VacancyView
import com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters.abstractions.IVacancyPresenter
import kotlinx.android.synthetic.main.fmt_vacancy.*
import ru.lliepmah.lib.UniversalAdapter
import javax.inject.Inject

/**
 * Created by lliepmah on 28.09.17
 */
class VacancyFragment : BaseFragment(), VacancyView {

    companion object {
        val FRAGMENT_KEY = "vacancy_fragment"
        val VACANCY_ID = "vacancy_id"
    }

    private lateinit var mParentScreenComponent: ParentScreenComponent

    @Inject
    lateinit var vacancyPresenter: IVacancyPresenter

    val adapter: UniversalAdapter by lazy {
        UniversalAdapter()
                UniversalAdapter(ResponseHolderBuilder(vacancyPresenter::onDownload))
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fmt_vacancy, container, false)

    private lateinit var mVacancyHolder: VacancyHolder

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        inject()
        vacancyPresenter.attachView(this)
        vacancyPresenter.loadVacancy(arguments.getInt(VACANCY_ID))

        vListSwipeLayout.setOnRefreshListener(vacancyPresenter::refresh)
        vListSwipeLayout.setColorSchemeResources(R.color.colorAccent)
        vListRvData.layoutManager = LinearLayoutManager(context)
        vListRvData.adapter = adapter

        mVacancyHolder = VacancyHolder(vVacancy, true, null, null, null, null)
    }

    private fun inject() {
        mParentScreenComponent = DaggerParentScreenComponent.builder()
                .rootComponent(rootComponent())
                .parentScreenModule(ParentScreenModule(getParentView()))
                .build()
                .also { it.inject(this) }
    }

    override fun showRefresh() {
        vVacancy.visibility = GONE
        vListSwipeLayout?.isRefreshing = true
    }

    override fun hideRefresh() {
        vVacancy.visibility = VISIBLE
        vListSwipeLayout?.isRefreshing = false
    }

    override fun setVacancy(vacancy: VacancyModel) {
        mVacancyHolder.bind(vacancy)
        adapter.addAll(vacancy.responses)
        adapter.notifyDataSetChanged()
    }


}