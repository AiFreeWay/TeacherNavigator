package com.teachernavigator.teachernavigator.presentation.screens.info.fragments

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.transition.TransitionManager
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.components.DaggerParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.components.ParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.modules.ParentScreenModule
import com.teachernavigator.teachernavigator.application.utils.rootComponent
import com.teachernavigator.teachernavigator.domain.models.Tag
import com.teachernavigator.teachernavigator.presentation.adapters.holders.TagVHBuilder
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.info.fragments.abstractions.TagsView
import com.teachernavigator.teachernavigator.presentation.screens.info.presenters.abstractions.ITagsPresenter
import kotlinx.android.synthetic.main.fmt_tags.*
import ru.lliepmah.lib.UniversalAdapter
import javax.inject.Inject

/**
 * Created by Arthur Korchagin on 10.10.17
 */
class TagsFragment : BaseFragment(), TagsView {

    companion object {
        val FRAGMENT_KEY = "tags_fragment"
    }

    private lateinit var mParentScreenComponent: ParentScreenComponent

    @Inject
    lateinit var presenter: ITagsPresenter

    val adapter: UniversalAdapter by lazy {
        UniversalAdapter(
                TagVHBuilder()
        )
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fmt_tags, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val allTab = vTabLayout
                .newTab()
                .setText(R.string.all_tags)
                .setTag(TagsView.ALL_TABS)

        val trends = vTabLayout
                .newTab()
                .setText(R.string.trends)
                .setTag(TagsView.TRENDS)

        vTabLayout.addTab(allTab)
        vTabLayout.addTab(trends)
        allTab.select()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        inject()
        presenter.attachView(this)
        presenter.refresh()
        vListSwipeLayout.setOnRefreshListener(presenter::refresh)
        vListSwipeLayout.setColorSchemeResources(R.color.colorAccent)
        vListRvData.layoutManager = LinearLayoutManager(context)
        vListRvData.adapter = adapter

        vTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) = presenter.refresh()
            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
            override fun onTabSelected(tab: TabLayout.Tab?) {
                presenter.onlyTrends = tab?.tag == TagsView.TRENDS
            }
        })

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) = Unit
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.text = text ?: ""
            }
        })
    }

    private fun inject() {
        mParentScreenComponent = DaggerParentScreenComponent.builder()
                .rootComponent(rootComponent())
                .parentScreenModule(ParentScreenModule(getParentView()))
                .build()
                .also { it.inject(this) }
    }

    override fun setTags(tags: List<Tag>) {
        adapter.clear()
        adapter.addAll(tags)
        TransitionManager.beginDelayedTransition(vListSwipeLayout)
        adapter.notifyDataSetChanged()
    }

    override fun showRefresh() {
        vListSwipeLayout?.isRefreshing = true
    }

    override fun hideRefresh() {
        vListSwipeLayout?.isRefreshing = false
    }


}