package com.teachernavigator.teachernavigator.presentation.screens.tape.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.adapters.holders.InfoPostVHBuilder
import com.teachernavigator.teachernavigator.presentation.models.PostModel
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IPostActionsController
import com.teachernavigator.teachernavigator.presentation.screens.tape.fragments.abstractions.PostsListView
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.abstractions.IPostsListPresenter
import com.teachernavigator.teachernavigator.presentation.utils.arg
import kotlinx.android.synthetic.main.fmt_posts.*
import ru.lliepmah.lib.UniversalAdapter
import javax.inject.Inject

/**
 * Created by root on 22.08.17
 */
class PostsListFragment : BaseFragment(), PostsListView {

    companion object {
        const val FRAGMENT_KEY = "PostsListFragment"

        const val POSTS_SOURCE_KEY = "posts_source"
        const val POST_TYPE_KEY = "post_type"
    }

    @Inject
    lateinit var presenter: IPostsListPresenter
    @Inject
    lateinit var postController: IPostActionsController

    val adapter: UniversalAdapter by lazy {
        UniversalAdapter(
                InfoPostVHBuilder(
                        postController::onLike,
                        postController::onDislike,
                        postController::onComments,
                        postController::onSave,
                        postController::onSubscribe,
                        postController::onReadMore,
                        postController::onComplain,
                        null,
                        null,
                        postController::onOpenProfile
                ))
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater?.inflate(R.layout.fmt_posts, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mParentScreenComponent.inject(this)

        presenter.attachView(this)
        postController.attachView(this)

        presenter.initialLoad(arg(POSTS_SOURCE_KEY), arg(POST_TYPE_KEY))

        fmtPostsSwipeLayout.setOnRefreshListener(presenter::refresh)
        fmtPostsSwipeLayout.setColorSchemeResources(R.color.colorAccent)
        fmtPostsRvList.layoutManager = LinearLayoutManager(context)
        fmtPostsRvList.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
        postController.detachView()
    }

    override fun setPosts(posts: List<PostModel>) {
        adapter.clear()
        adapter.addAll(posts)
        adapter.notifyDataSetChanged()
    }

    override fun showNoDataText() {
        fmtPostsTvNoData.visibility = View.VISIBLE
    }

    override fun hideNoDataText() {
        fmtPostsTvNoData.visibility = View.GONE
    }


    override fun showRefresh() {
        fmtPostsSwipeLayout?.isRefreshing = true
    }

    override fun hideRefresh() {
        fmtPostsSwipeLayout?.isRefreshing = false
    }

    override fun updatePost(post: PostModel) = adapter.notifyItemChanged(adapter.indexOf(post))

    fun refresh() = presenter?.refresh()


}