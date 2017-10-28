package com.teachernavigator.teachernavigator.presentation.screens.info.fragments.abstractions

import com.neovisionaries.ws.client.WebSocketState
import com.teachernavigator.teachernavigator.presentation.models.MessageModel
import com.teachernavigator.teachernavigator.presentation.screens.common.ChildView

interface ChatView : ChildView {
    fun hideRefresh()
    fun showRefresh()

    fun setMembersCount(membersCount: Int)
    fun setState(state: WebSocketState)

    fun setMessages(messages: List<MessageModel>)
    fun addMessage(message: MessageModel)
    fun clearInput()

}