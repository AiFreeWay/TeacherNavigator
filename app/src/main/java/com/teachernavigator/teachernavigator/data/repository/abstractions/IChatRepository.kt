package com.teachernavigator.teachernavigator.data.repository.abstractions

import com.teachernavigator.teachernavigator.domain.models.Message
import io.reactivex.Single

/**
 * Created by Arthur Korchagin on 27.10.17
 */
interface IChatRepository {

    fun loadMessages(): Single<List<Message>>

    fun loadMessage(messageId: Int): Single<Message>

    fun accessToken(): String

}