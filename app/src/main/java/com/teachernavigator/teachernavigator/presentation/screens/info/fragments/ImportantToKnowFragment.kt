package com.teachernavigator.teachernavigator.presentation.screens.info.fragments

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
import com.teachernavigator.teachernavigator.presentation.adapters.holders.InfoPostVHBuilder
import com.teachernavigator.teachernavigator.presentation.adapters.holders.InfoVHBuilder
import com.teachernavigator.teachernavigator.presentation.adapters.holders.TitleVHBuilder
import com.teachernavigator.teachernavigator.presentation.models.Info
import com.teachernavigator.teachernavigator.presentation.models.PostModel
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.info.fragments.abstractions.ImportantToKnowView
import com.teachernavigator.teachernavigator.presentation.screens.info.presenters.abstractions.IImportantToKnowPresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IPostActionsController
import kotlinx.android.synthetic.main.fmt_important_to_know.*
import ru.lliepmah.lib.UniversalAdapter
import javax.inject.Inject

/**
 * Created by lliepmah on 05.10.17
 */
class ImportantToKnowFragment : BaseFragment(), ImportantToKnowView {

    companion object {
        val FRAGMENT_KEY = "important_to_know_fragment"
    }

    @Inject
    lateinit var presenter: IImportantToKnowPresenter
    @Inject
    lateinit var postController: IPostActionsController

    private var mPostsPosition: Int = 0

    val adapter: UniversalAdapter by lazy {
        UniversalAdapter(
                InfoPostVHBuilder(
                        postController::onLike,
                        postController::onDislike,
                        postController::onComments,
                        postController::onSave,
                        null,
                        null,
                        null,
                        null
                ),
                InfoVHBuilder(presenter::onThemeChanged),
                TitleVHBuilder())
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fmt_important_to_know, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mParentScreenComponent.inject(this)
        presenter.attachView(this)
        postController.attachView(this)

        vListSwipeLayout.setOnRefreshListener(presenter::refresh)
        vListSwipeLayout.setColorSchemeResources(R.color.colorAccent)
        vListRvData.layoutManager = LinearLayoutManager(context)
        vListRvData.adapter = adapter
    }

    override fun setThemes(infoThemes: List<Info>) {
        adapter.clear()
        adapter.add(getString(R.string.label_choose_theme))
        adapter.addAll(infoThemes)
        mPostsPosition = adapter.itemCount
    }

    override fun setInfoPosts(posts: List<PostModel>) {
        adapter.removeItems(mPostsPosition, adapter.itemCount - 1)
        adapter.addAll(posts)
        adapter.notifyDataSetChanged()
    }

    override fun updatePost(post: PostModel) {
        adapter.notifyItemChanged(adapter.indexOf(post))
    }

    override fun showRefresh() {
        vListSwipeLayout?.isRefreshing = true
    }

    override fun hideRefresh() {
        vListSwipeLayout?.isRefreshing = false
    }

}