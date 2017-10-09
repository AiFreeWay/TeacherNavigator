package com.teachernavigator.teachernavigator.presentation.adapters.holders

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.models.CommentModel
import com.teachernavigator.teachernavigator.presentation.utils.find
import com.teachernavigator.teachernavigator.presentation.utils.listenClickBy
import com.teachernavigator.teachernavigator.presentation.utils.setImageOrPlaceholder
import com.teachernavigator.teachernavigator.presentation.utils.setTextOrHide
import ru.lliepmah.HolderBuilder
import ru.lliepmah.lib.DefaultViewHolder

/**
 * Created by lliepmah on 08.10.17
 */
@HolderBuilder(R.layout.v_comment_card)
class CommentVH(itemView: View,
                onSubscribeListener: OnSubscribeListener?,
                onBranchListener: OnBranchListener?) : DefaultViewHolder<CommentModel>(itemView) {

    private val context = itemView.context

    private val ivAvatar: ImageView = itemView.find(R.id.v_comment_iv_avatar)
    private val tvPostAuthorName: TextView = itemView.find(R.id.v_comment_tv_post_author_name)
    private val tvAuthorName: TextView = itemView.find(R.id.v_comment_tv_author_name)
    private val tvPostTime: TextView = itemView.find(R.id.v_comment_tv_post_time)
    private val ivSubscribe: ImageView = itemView.find(R.id.v_comment_iv_subscribe)
    private val tvText: TextView = itemView.find(R.id.v_comment_tv_text)

    private val llOpenBranch: View = itemView.find(R.id.v_comment_ll_open_branch)
    private val btnOpenBranch: Button = itemView.find(R.id.v_comment_btn_open_branch)

    private var mComment: CommentModel? = null

    init {
        ivSubscribe listenClickBy onSubscribeListener andReturnModelOrHide { mComment }
        btnOpenBranch listenClickBy onBranchListener andReturnModelOrHide { mComment }
        llOpenBranch.visibility = btnOpenBranch.visibility
    }

    override fun bind(comment: CommentModel?) {
        mComment = comment

        ivAvatar.setImageOrPlaceholder(comment?.userAvatar)
        tvAuthorName.setTextOrHide(comment?.userName)
        tvPostAuthorName.setTextOrHide(comment?.postAuthorName)

        tvText.setTextOrHide(comment?.message)
        tvPostTime.setTextOrHide(comment?.timeAgo)
    }

}

typealias OnSubscribeListener = (CommentModel) -> Unit
typealias OnBranchListener = (CommentModel) -> Unit