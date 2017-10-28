package com.teachernavigator.teachernavigator.domain.interactors.abstractions

import com.teachernavigator.teachernavigator.domain.models.ChatEnvelope
import com.teachernavigator.teachernavigator.domain.models.Message
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Arthur Korchagin on 27.10.17
 */
interface IChatInteractor {

    fun loadMessages(): Single<List<Message>>

    fun loadMessage(messageId: Int): Single<Message>

    fun sendMessage(message: String): Single<Unit>

    fun connect(): Observable<ChatEnvelope>

}