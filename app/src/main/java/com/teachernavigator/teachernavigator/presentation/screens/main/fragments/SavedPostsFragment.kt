package com.teachernavigator.teachernavigator.presentation.screens.main.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.components.DaggerParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.components.ParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.modules.ParentScreenModule
import com.teachernavigator.teachernavigator.application.utils.rootComponent
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.adapters.MultyRvAdapter
import com.teachernavigator.teachernavigator.presentation.adapters.holders.SavedPostHolder
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.SavedPostsView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.ISavedPostsPresenter
import kotlinx.android.synthetic.main.fmt_list.*
import javax.inject.Inject

/**
 * Created by root on 08.09.17
 */
class SavedPostsFragment : BaseFragment(), SavedPostsView {

    companion object {
        val FRAGMENT_KEY = "saved_posts_fragment"
    }

    private lateinit var mParentScreenComponent: ParentScreenComponent

    @Inject
    lateinit var presenter: ISavedPostsPresenter

    private lateinit var mAdapter: MultyRvAdapter<Post>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater?.inflate(R.layout.fmt_list, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        inject()
        presenter.attachView(this)

        setHasOptionsMenu(true)
        mAdapter = MultyRvAdapter(SavedPostHolder(context, presenter.getPostControllerFacade()))
        fmt_list_rv_list.layoutManager = LinearLayoutManager(context)
        fmt_list_rv_list.adapter = mAdapter
        presenter.getSavedPosts()
    }

    private fun inject() {
        mParentScreenComponent = DaggerParentScreenComponent.builder()
                .rootComponent(rootComponent())
                .parentScreenModule(ParentScreenModule(getParentView()))
                .build()
                .also { it.inject(this) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    override fun loadSavedPosts(posts: List<Post>) {
        mAdapter.loadData(posts)
    }

    override fun showNoDataText() {
        fmt_list_tv_no_data.visibility = View.VISIBLE
    }

    override fun hideNoDataText() {
        fmt_list_tv_no_data.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_refresh_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                presenter.openPostSearchScreen()
            }
            R.id.action_refresh -> {
                presenter.refresh()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}