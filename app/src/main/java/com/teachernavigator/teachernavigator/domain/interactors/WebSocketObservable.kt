package com.teachernavigator.teachernavigator.domain.interactors

import android.util.Log
import android.util.Log.d
import com.google.gson.Gson
import com.neovisionaries.ws.client.*
import com.teachernavigator.teachernavigator.domain.models.ChatEnvelope
import io.reactivex.ObservableEmitter

/**
 * Created by Arthur Korchagin on 28.10.17
 */
class WebSocketObservable(private val emitter: ObservableEmitter<ChatEnvelope>,
                          private val gson: Gson) : WebSocketListener {

    override fun handleCallbackError(websocket: WebSocket?, cause: Throwable?) {
        cause?.let { emitter.onNext(ChatEnvelope.ChatError(it)) }
    }

    override fun onFrame(websocket: WebSocket?, frame: WebSocketFrame?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onThreadCreated(websocket: WebSocket?, threadType: ThreadType?, thread: Thread?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onThreadStarted(websocket: WebSocket?, threadType: ThreadType?, thread: Thread?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStateChanged(websocket: WebSocket?, newState: WebSocketState?) {
        d(javaClass.name, "-> onStateChanged ->" + newState)

        newState?.let { emitter.onNext(ChatEnvelope.ChatStatus(it)) }
    }

    override fun onTextMessageError(websocket: WebSocket?, cause: WebSocketException?, data: ByteArray?) {
        cause?.let { emitter.onNext(ChatEnvelope.ChatError(it)) }
    }

    override fun onTextFrame(websocket: WebSocket?, frame: WebSocketFrame?) {
//        frame?.let { emitter.onNext(ChatEnvelope.ChatMessage(it.payloadText)) }
    }

    override fun onUnexpectedError(websocket: WebSocket?, cause: WebSocketException?) {
        cause?.let { emitter.onNext(ChatEnvelope.ChatError(it)) }
    }

    override fun onConnectError(websocket: WebSocket?, cause: WebSocketException?) {
        cause?.let { emitter.onNext(ChatEnvelope.ChatError(it)) }
    }

    override fun onSendError(websocket: WebSocket?, cause: WebSocketException?, frame: WebSocketFrame?) {
        cause?.let { emitter.onNext(ChatEnvelope.ChatError(it)) }
    }

    override fun onFrameUnsent(websocket: WebSocket?, frame: WebSocketFrame?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDisconnected(websocket: WebSocket?, serverCloseFrame: WebSocketFrame?, clientCloseFrame: WebSocketFrame?, closedByServer: Boolean) {
        d(javaClass.name, "-> onDisconnected ->")

        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSendingFrame(websocket: WebSocket?, frame: WebSocketFrame?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBinaryFrame(websocket: WebSocket?, frame: WebSocketFrame?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPingFrame(websocket: WebSocket?, frame: WebSocketFrame?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSendingHandshake(websocket: WebSocket?, requestLine: String?, headers: MutableList<Array<String>>?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTextMessage(websocket: WebSocket?, text: String?) {
        d(javaClass.name, "-> onTextMessage -> thread=${Thread.currentThread().name}")

        emitter.onNext(gson.fromJson(text, ChatEnvelope::class.java))
    }

    override fun onFrameError(websocket: WebSocket?, cause: WebSocketException?, frame: WebSocketFrame?) {
        cause?.let { emitter.onNext(ChatEnvelope.ChatError(it)) }
    }

    override fun onCloseFrame(websocket: WebSocket?, frame: WebSocketFrame?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBinaryMessage(websocket: WebSocket?, binary: ByteArray?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onContinuationFrame(websocket: WebSocket?, frame: WebSocketFrame?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnected(websocket: WebSocket?, headers: MutableMap<String, MutableList<String>>?) {
        Log.d(javaClass.name, "-> onConnected ->")
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFrameSent(websocket: WebSocket?, frame: WebSocketFrame?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onThreadStopping(websocket: WebSocket?, threadType: ThreadType?, thread: Thread?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(websocket: WebSocket?, cause: WebSocketException?) {
        cause?.let { emitter.onNext(ChatEnvelope.ChatError(it)) }
    }

    override fun onMessageDecompressionError(websocket: WebSocket?, cause: WebSocketException?, compressed: ByteArray?) {
        cause?.let { emitter.onNext(ChatEnvelope.ChatError(it)) }
    }

    override fun onPongFrame(websocket: WebSocket?, frame: WebSocketFrame?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onMessageError(websocket: WebSocket?, cause: WebSocketException?, frames: MutableList<WebSocketFrame>?) {
        cause?.let { emitter.onNext(ChatEnvelope.ChatError(it)) }
    }

}
