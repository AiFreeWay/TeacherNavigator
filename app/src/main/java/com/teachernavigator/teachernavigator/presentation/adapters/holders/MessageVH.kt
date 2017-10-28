package com.teachernavigator.teachernavigator.presentation.adapters.holders

import android.support.v4.content.ContextCompat
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.models.MessageModel
import com.teachernavigator.teachernavigator.presentation.utils.ImageLoader
import com.teachernavigator.teachernavigator.presentation.utils.find
import ru.lliepmah.HolderBuilder
import ru.lliepmah.lib.DefaultViewHolder

/**
 * Created by Arthur Korchagin on 27.10.17
 */
@HolderBuilder(R.layout.v_message)
class MessageVH(itemView: View) : DefaultViewHolder<MessageModel>(itemView) {

    private val vMgsTvTime: TextView = itemView.find(R.id.vMgsTvTime)
    private val vMgsTvName: TextView = itemView.find(R.id.vMgsTvName)
    private val vMgsTvMessage: TextView = itemView.find(R.id.vMgsTvMessage)
    private val vMgsIvAvatar: ImageView = itemView.find(R.id.vMgsIvAvatar)
    private val vMgsLBubble: View = itemView.find(R.id.vMgsLBubble)

    private val context = itemView.context

    private var mMessage: MessageModel? = null

    override fun bind(message: MessageModel?) {
        mMessage = message

        val isNotMine = message?.isMine == false

        vMgsLBubble.setBackgroundResource(if (isNotMine) R.drawable.bg_msg else R.drawable.bg_msg_out)
        vMgsTvName.setTextColor(ContextCompat.getColor(context, if (isNotMine) R.color.colorAccent else R.color.black))
        vMgsIvAvatar.visibility = if (isNotMine) VISIBLE else GONE

        val avatar = message?.userAvatar
        if (isNotMine && avatar != null) {
            ImageLoader.load(context, avatar, vMgsIvAvatar)
        }

        vMgsTvTime.text = message?.timeString ?: ""
        vMgsTvName.text = message?.userName ?: ""
        vMgsTvMessage.text = message?.text ?: ""

    }

}