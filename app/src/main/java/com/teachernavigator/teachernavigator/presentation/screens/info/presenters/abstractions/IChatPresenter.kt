package com.teachernavigator.teachernavigator.presentation.screens.info.presenters.abstractions

import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.info.fragments.abstractions.ChatView

/**
 * @author Created by Arthur Korchagin on 27.10.17
 */

interface IChatPresenter : ViewAttacher<ChatView> {

    fun refresh()

    fun loadMessagesAndConnect()
    fun sendMessage(text: CharSequence)
    fun connect()

}