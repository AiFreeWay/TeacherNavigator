package com.teachernavigator.teachernavigator.presentation.screens.main.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.adapters.holders.CommentVHBuilder
import com.teachernavigator.teachernavigator.presentation.adapters.holders.InfoPostVHBuilder
import com.teachernavigator.teachernavigator.presentation.models.PostModel
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.PostCommentsView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IPostActionsController
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IPostCommentsPresenter
import kotlinx.android.synthetic.main.fmt_post_comments.*
import ru.lliepmah.lib.UniversalAdapter
import javax.inject.Inject

/**
 * Created by lliepmah on 08.10.17
 */
class PostCommentsFragment : BaseFragment(), PostCommentsView {

    companion object {
        val FRAGMENT_KEY = "post_comments_fragment"

        val ARG_POST_ID = "post_id"
        val ARG_POST_TYPE = "post_type"
    }

    @Inject
    lateinit var presenter: IPostCommentsPresenter
    @Inject
    lateinit var postController: IPostActionsController

    val adapter: UniversalAdapter by lazy {
        UniversalAdapter(
                InfoPostVHBuilder(
                        postController::onLike,
                        postController::onDislike,
                        null,
                        postController::onSave,
                        null,
                        null, // TODO postConroller::onReadMore
                        null
                ),
                CommentVHBuilder(
                        null, // TODO presenter::onBranch
                        null // TODO presenter::onSubscribe
                )
        )
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fmt_post_comments, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postCommentsIvSend.setOnClickListener { sendComment() }
    }

    private fun sendComment() {
        presenter.sendComment(postCommentsEtSendText.text)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mParentScreenComponent.inject(this)

        presenter.attachView(this)
        postController.attachView(this)

        vListSwipeLayout.setOnRefreshListener(presenter::refresh)
        vListSwipeLayout.setColorSchemeResources(R.color.colorAccent)
        vListRvData.layoutManager = LinearLayoutManager(context)
        vListRvData.adapter = adapter

        presenter.initPost(arguments.getInt(ARG_POST_ID), arguments.getInt(ARG_POST_TYPE))
    }

    override fun setPost(post: PostModel) {
        adapter.clear()
        adapter.add(post)
        adapter.addAll(post.comments)
        adapter.notifyDataSetChanged()
    }

    override fun scrollToLast() =
            vListRvData.layoutManager.smoothScrollToPosition(vListRvData, null, adapter.itemCount - 1)

    override fun showRefresh() {
        postCommentsIvSend.isEnabled = false
        postCommentsEtSendText.isEnabled = false
        vListSwipeLayout?.isRefreshing = true
    }

    override fun hideRefresh() {
        postCommentsIvSend.isEnabled = true
        postCommentsEtSendText.isEnabled = true
        vListSwipeLayout?.isRefreshing = false
    }

    override fun clearField() {
        postCommentsEtSendText.setText(R.string.empty)
    }

    override fun updatePost(post: PostModel) =
            adapter.notifyItemChanged(adapter.indexOf(post))

}