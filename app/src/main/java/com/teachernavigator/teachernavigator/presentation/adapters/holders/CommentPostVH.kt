package com.teachernavigator.teachernavigator.presentation.adapters.holders

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.TextView
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.models.PostCommentModel
import com.teachernavigator.teachernavigator.presentation.utils.find
import com.teachernavigator.teachernavigator.presentation.utils.listenClickBy
import ru.lliepmah.HolderBuilder
import ru.lliepmah.lib.DefaultViewHolder

/**
 * Created by lliepmah on 07.10.17
 */
@HolderBuilder(R.layout.v_info_post)
class CommentPostVH(itemView: View,
                    onLikeListener: OnLikeListener?,
                    onDislikeListener: OnDislikeListener?,
                    onCommentsListener: OnCommentsListener?,
                    onSaveListener: OnSaveListener?,
                    onSubscribeListener: OnSubscribePostListener?,
                    onReadMoreListener: OnReadMoreListener?,
                    onComplaintListener: OnComplaintListener?,
                    onPollPassListener: OnPollPassListener?

) : DefaultViewHolder<PostCommentModel>(itemView) {

    private val context = itemView.context

    private val postHolder = InfoPostVH(itemView, onLikeListener, onDislikeListener, onCommentsListener,
            onSaveListener, onSubscribeListener, onReadMoreListener, onComplaintListener,
            onPollPassListener)

    private val lYourComment: View = itemView.find(R.id.v_post_l_your_comment)
    private val tvCommentText: TextView = itemView.find(R.id.v_post_tv_comment_text)
    private val btnOpenBranch: Button = itemView.find(R.id.v_post_btn_open_branch)


    private var mModel: PostCommentModel? = null

    init {
        postHolder.ivSave.visibility = GONE
        postHolder.tvComments.visibility = GONE
        lYourComment.visibility = VISIBLE
        btnOpenBranch.visibility = VISIBLE

        btnOpenBranch listenClickBy onCommentsListener andReturnModel { mModel?.postModel }
    }


    override fun bind(postComment: PostCommentModel?) {
        mModel = postComment

        postHolder.bind(postComment?.postModel)

        tvCommentText.text = postComment?.commentModel?.message ?: ""
    }


}