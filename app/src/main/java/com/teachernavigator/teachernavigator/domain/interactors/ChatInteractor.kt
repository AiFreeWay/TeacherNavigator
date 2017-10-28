package com.teachernavigator.teachernavigator.domain.interactors

import android.util.Log.d
import com.google.gson.Gson
import com.neovisionaries.ws.client.WebSocket
import com.neovisionaries.ws.client.WebSocketFactory
import com.teachernavigator.teachernavigator.data.network.NetworkController
import com.teachernavigator.teachernavigator.data.repository.abstractions.IChatRepository
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IChatInteractor
import com.teachernavigator.teachernavigator.domain.models.ChatEnvelope
import com.teachernavigator.teachernavigator.domain.models.Message
import com.teachernavigator.teachernavigator.presentation.utils.applySchedulers
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Arthur Korchagin on 27.10.17
 */
class ChatInteractor
@Inject constructor(private val mRepository: IChatRepository,
                    private val gson : Gson) : IChatInteractor {

    private val webSocketFactory = WebSocketFactory()
    private val socketUrl: String
        get() = "ws://${NetworkController.DOMAIN}/chat/stream?token=${mRepository.accessToken()}"

    private var socket: WebSocket? = null

    override fun loadMessages(): Single<List<Message>>
            = mRepository.loadMessages()
            .applySchedulers()

    override fun loadMessage(messageId: Int): Single<Message>
            = mRepository.loadMessage(messageId)
            .applySchedulers()

    override fun sendMessage(message: String): Single<Unit> = Single.create {

        try {

            socket?.sendText("{\"text\":\"$message\"}")
            it.onSuccess(Unit)

        } catch (error: Throwable) {

            it.onError(error)

        }

    }

    override fun connect() = Observable.create<ChatEnvelope> {
        try {

            socket?.clearListeners()

            val socket = createSocket()
            socket.addListener(WebSocketObservable(it, gson))
            socket.connect()
            this.socket = socket

        } catch (error: Throwable) {
            it.onError(error)
        }

    }
            .observeOn(Schedulers.io())

            .doOnNext {
                d(javaClass.name, "-> doOnNext -> thread=${Thread.currentThread().name}")
            }

            .doOnDispose { disconnect() }

            .applySchedulers()

    private fun disconnect() {

        d(javaClass.name, "-> disconnect -> thread=${Thread.currentThread().name}) ")

        socket?.disconnect()
        socket?.clearListeners()

    }

    private fun createSocket() =
            webSocketFactory.createSocket(socketUrl)

}