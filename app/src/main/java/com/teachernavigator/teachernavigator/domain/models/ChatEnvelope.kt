package com.teachernavigator.teachernavigator.domain.models

import com.google.gson.annotations.SerializedName
import com.neovisionaries.ws.client.WebSocketState

/**
 * Created by Arthur Korchagin on 28.10.17
 */
sealed class ChatEnvelope {


    data class ChatStatus(val status: WebSocketState) : ChatEnvelope()

    data class ChatMembers(@SerializedName("amount_of_members") val membersCount: Int) : ChatEnvelope()

    data class ChatMessage(val message: Message) : ChatEnvelope()

    data class ChatError(val error: Throwable) : ChatEnvelope()

}