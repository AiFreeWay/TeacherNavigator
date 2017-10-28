package com.teachernavigator.teachernavigator.presentation.screens.info.fragments

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.neovisionaries.ws.client.WebSocketState
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.adapters.holders.MessageVHBuilder
import com.teachernavigator.teachernavigator.presentation.models.MessageModel
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.info.fragments.abstractions.ChatView
import com.teachernavigator.teachernavigator.presentation.screens.info.presenters.abstractions.IChatPresenter
import kotlinx.android.synthetic.main.fmt_chat.*
import ru.lliepmah.lib.UniversalAdapter
import javax.inject.Inject

/**
 * Created by Arthur Korchagin on 27.10.17
 */
class ChatFragment : BaseFragment(), ChatView {

    companion object {

        val FRAGMENT_KEY = "chat_fragment"
    }

    @Inject
    lateinit var presenter: IChatPresenter

    val adapter: UniversalAdapter by lazy {
        UniversalAdapter(
                MessageVHBuilder()
        )
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fmt_chat, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mParentScreenComponent.inject(this)
        presenter.attachView(this)
        presenter.loadMessagesAndConnect()

        fmtChatSwipeLayout.setOnRefreshListener(presenter::refresh)
        fmtChatSwipeLayout.setColorSchemeResources(R.color.colorAccent)
        fmtChatRvList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
        fmtChatRvList.adapter = adapter

        fmtChatBtnSend.setOnClickListener { sendMessage() }
    }

    private fun sendMessage() {
        presenter.sendMessage(fmtChatEtSend.text)
    }

    override fun setMembersCount(membersCount: Int) {
        fmtChatTvStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.green))
        fmtChatTvStatus.text = resources.getQuantityString(R.plurals.members_in_chat, membersCount, membersCount)
    }

    override fun clearInput() {
        fmtChatEtSend.setText(R.string.empty)
    }

    override fun setState(state: WebSocketState) = when (state) {

        WebSocketState.CREATED -> created()
        WebSocketState.CONNECTING -> connecting()
        WebSocketState.OPEN -> open()
        WebSocketState.CLOSING -> closing()
        WebSocketState.CLOSED -> closed()

    }

    fun created() {
        fmtChatTvStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.grey))
        fmtChatTvStatus.text = getString(R.string.disconnected)
        fmtChatTvStatus.setOnClickListener {}
    }

    fun connecting() {
        fmtChatTvStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow))
        fmtChatTvStatus.text = getString(R.string.connecting)
        fmtChatTvStatus.setOnClickListener {}
    }

    fun open() {
        fmtChatTvStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.green))
        fmtChatTvStatus.text = getString(R.string.connected)
        fmtChatTvStatus.setOnClickListener {}
    }

    fun closing() {
        fmtChatTvStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow))
        fmtChatTvStatus.text = getString(R.string.disconnecting)
        fmtChatTvStatus.setOnClickListener {}
    }

    fun closed() {
        fmtChatTvStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.grey))
        fmtChatTvStatus.text = getString(R.string.offline_retry)
        fmtChatTvStatus.setOnClickListener { presenter.connect() }
    }

    override fun addMessage(message: MessageModel) {
        adapter.add(0, message)
        adapter.notifyItemInserted(0)
        fmtChatRvList.layoutManager.scrollToPosition(0)
    }

    override fun setMessages(messages: List<MessageModel>) {
        adapter.clear()
        adapter.addAll(messages)
        adapter.notifyDataSetChanged()
    }

    override fun showRefresh() {
        fmtChatSwipeLayout?.isRefreshing = true
    }

    override fun hideRefresh() {
        fmtChatSwipeLayout?.isRefreshing = false
    }

}