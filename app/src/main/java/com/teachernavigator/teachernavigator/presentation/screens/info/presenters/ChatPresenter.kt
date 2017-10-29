package com.teachernavigator.teachernavigator.presentation.screens.info.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log.d
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IChatInteractor
import com.teachernavigator.teachernavigator.domain.models.ChatEnvelope
import com.teachernavigator.teachernavigator.presentation.models.MessageModel
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.info.fragments.abstractions.ChatView
import com.teachernavigator.teachernavigator.presentation.screens.info.presenters.abstractions.IChatPresenter
import com.teachernavigator.teachernavigator.presentation.transformers.MessageTransformer
import com.teachernavigator.teachernavigator.presentation.transformers.transformListEntity
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by Arthur Korchagin on 27.10.17
 */
@PerParentScreen
class ChatPresenter
@Inject constructor(val router: Router,
                    private val chatInteractor: IChatInteractor,
                    private val messageTransformer: MessageTransformer) : BasePresenter<ChatView>(), IChatPresenter {
    override fun connect() {
        mDisposables.clear()
        addDissposable(chatInteractor.connect()
                .subscribe(this::onChatEnvelope, this::onError))
    }

    override fun loadMessagesAndConnect() {
        refresh()
    }

    fun onMessage(stub: Unit) {
        mView?.clearInput()
    }

    override fun sendMessage(text: CharSequence) {

        addDissposable(chatInteractor.sendMessage(text.toString())
                .subscribe(this::onMessage, this::onError))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        mView?.getParentView()?.setToolbarTitle(R.string.chat)
        addDissposable(chatInteractor.connect()
                .subscribe(this::onChatEnvelope, this::onError))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    private fun onChatEnvelope(envelope: ChatEnvelope) {

        d(javaClass.name, "-> onChatEnvelope -> threadName=${Thread.currentThread().name}")

        when (envelope) {

            is ChatEnvelope.ChatMessage -> mView?.addMessage(messageTransformer.transform(envelope.message))
            is ChatEnvelope.ChatStatus -> mView?.setState(envelope.status)
            is ChatEnvelope.ChatMembers -> mView?.setMembersCount(envelope.membersCount)

            is ChatEnvelope.ChatError -> d(javaClass.name, "-> error -> ${envelope.error}")
        }
    }


    override fun refresh() {
        addDissposable(chatInteractor.loadMessages()
                .transformListEntity(messageTransformer)
                .doOnSubscribe { startProgress() }
                .subscribe(this::onLoaded, this::onError))
    }

    fun onLoaded(messages: List<MessageModel>) {
        stopProgress()
        mView?.setMessages(messages)
    }

    fun onError(error: Throwable) {
        stopProgress()
        doOnError(error)
    }

    private fun startProgress() {
        mView?.getParentView()?.startProgress()
        mView?.showRefresh()
    }

    private fun stopProgress() {
        mView?.getParentView()?.stopProgress()
        mView?.hideRefresh()
    }

}