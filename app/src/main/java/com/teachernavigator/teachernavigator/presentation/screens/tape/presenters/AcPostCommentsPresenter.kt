package com.teachernavigator.teachernavigator.presentation.screens.tape.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.text.TextUtils
import android.widget.Toast
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.components.DaggerParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.modules.ParentScreenModule
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.ICommentsInteractor
import com.teachernavigator.teachernavigator.domain.models.Comment
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacade
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.activities.absctraction.PostCommentsView
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.abstractions.IPostCommentsPresenter
import javax.inject.Inject

/**
 * Created by root on 08.09.17.
 */
class AcPostCommentsPresenter : BasePresenter<PostCommentsView>(), IPostCommentsPresenter {

    @Inject
    lateinit var mCommentsInteractor: ICommentsInteractor
    @Inject
    lateinit var mPostControllerFacade: IPostControllerFacade

    init {
        Logger.logDebug("created PRESENTER AcPostCommentsPresenter")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun attachView(view: PostCommentsView) {
        super.attachView(view)
        inject()
    }

    override fun doOnError(error: Throwable) {
        super.doOnError(error)
        mView!!.stopProgress()
        mView!!.unlockUi()
        Toast.makeText(mView!!.getActivity(), mView!!.getActivity().getString(R.string.error_throwed), Toast.LENGTH_SHORT).show()
    }

    override fun navigateBack() {
        mView!!.getActivity().finish()
    }

    override fun getIPostControllerFacade(): IPostControllerFacade = mPostControllerFacade

    override fun doComment(post: Post, text: String) {
        if (!TextUtils.isEmpty(text))
            addDissposable(mCommentsInteractor.comment(post, text)
                    .doOnSubscribe { this::doOnSubscribeOnSendPost }
                    .subscribe(this::doOnCommented, this::doOnError))
    }

    private fun doOnSubscribeOnSendPost() {
        mView!!.startProgress()
        mView!!.lockUi()
    }

    private fun doOnCommented(comment: Comment) {
        Logger.testLog("doOnCommented")
        mView!!.stopProgress()
        mView!!.loadComment(comment)
        mView!!.unlockUi()
    }

    private fun inject() {
        DaggerParentScreenComponent.builder()
                .rootComponent(getRootComponent(mView!!.getActivity()))
                .parentScreenModule(ParentScreenModule(mView!!))
                .build()
                .inject(this)
    }
}