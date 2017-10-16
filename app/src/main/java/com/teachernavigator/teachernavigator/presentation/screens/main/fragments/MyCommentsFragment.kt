package com.teachernavigator.teachernavigator.presentation.screens.main.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.adapters.holders.CommentPostVHBuilder
import com.teachernavigator.teachernavigator.presentation.models.PostCommentModel
import com.teachernavigator.teachernavigator.presentation.models.PostModel
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.MyCommentsView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IMyCommentsPresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IPostActionsController
import kotlinx.android.synthetic.main.fmt_my_comments.*
import kotlinx.android.synthetic.main.fmt_posts.*
import ru.lliepmah.lib.UniversalAdapter
import javax.inject.Inject

/**
 * Created by root on 08.09.17
 */
class MyCommentsFragment : BaseFragment(), MyCommentsView {

    companion object {
        val FRAGMENT_KEY = "my_comments_fragment"
    }

    @Inject
    lateinit var presenter: IMyCommentsPresenter
    @Inject
    lateinit var postController: IPostActionsController

    val adapter: UniversalAdapter by lazy {
        UniversalAdapter(
                CommentPostVHBuilder(
                        postController::onLike,
                        postController::onDislike,
                        postController::onComments,
                        postController::onSave,
                        postController::onSubscribe,
                        postController::onReadMore,
                        postController::onComplain,
                        null
                ))
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater?.inflate(R.layout.fmt_my_comments, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mParentScreenComponent.inject(this)

        presenter.attachView(this)
        postController.attachView(this)

        presenter.initialLoad()

        fmtMyCommentsSwipeLayout.setOnRefreshListener(presenter::refresh)
        fmtMyCommentsSwipeLayout.setColorSchemeResources(R.color.colorAccent)
        fmtMyCommentsRvList.layoutManager = LinearLayoutManager(context)
        fmtMyCommentsRvList.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
        postController.detachView()
    }

    override fun loadComments(posts: List<PostCommentModel>) {
        adapter.clear()
        adapter.addAll(posts)
        adapter.notifyDataSetChanged()
    }

    override fun showNoDataText() {
        fmtMyCommentsTvNoData.visibility = View.VISIBLE
    }

    override fun hideNoDataText() {
        fmtMyCommentsTvNoData.visibility = View.GONE
    }

    override fun updatePost(post: PostModel) = adapter.notifyItemChanged(adapter.indexOf(post))

    override fun showRefresh() {
        fmtPostsSwipeLayout?.isRefreshing = true
    }

    override fun hideRefresh() {
        fmtPostsSwipeLayout?.isRefreshing = false
    }

}