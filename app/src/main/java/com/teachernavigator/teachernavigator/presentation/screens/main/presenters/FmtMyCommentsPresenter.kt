package com.teachernavigator.teachernavigator.presentation.screens.main.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.widget.Toast
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.ICommentsInteractor
import com.teachernavigator.teachernavigator.domain.models.Comment
import com.teachernavigator.teachernavigator.presentation.screens.base.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.MyCommentsView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IMyCommentsPresenter
import javax.inject.Inject

/**
 * Created by root on 08.09.17.
 */
class FmtMyCommentsPresenter : BasePresenter<MyCommentsView>(), IMyCommentsPresenter {

    @Inject
    lateinit var mCommentsInteractor: ICommentsInteractor

    private var mTapeType: Int = -1

    init {
        Logger.logDebug("created PRESENTER FmtMyCommentsPresenter")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        mView!!.getParentView().setToolbarTitle(R.string.my_comments)
        loadComments()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun attachView(view: MyCommentsView) {
        super.attachView(view)
        inject()
    }

    override fun doOnError(error: Throwable) {
        super.doOnError(error)
        mView!!.getParentView().stopProgress()
        mView!!.showNoDataText()
        Toast.makeText(mView!!.getContext(), mView!!.getContext().getString(R.string.error_throwed), Toast.LENGTH_SHORT).show()
    }

    override fun openPost(postId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadComments() {
        addDissposable(mCommentsInteractor.getMyComments()
                .doOnSubscribe { this::doOnSubscribeOnGetPosts }
                .subscribe(this::doOnGetComments, this::doOnError))
    }

    private fun doOnGetComments(comments: List<Comment>) {
        mView!!.getParentView().stopProgress()
        mView!!.loadComments(comments)

        if (comments.isNotEmpty())
            mView!!.hideNoDataText()
        else
            mView!!.showNoDataText()
    }

    private fun doOnSubscribeOnGetPosts() {
        mView!!.getParentView().startProgress()
        mView!!.hideNoDataText()
    }

    private fun inject() {
        mView!!.getParentView()
                .getParentScreenComponent()
                .inject(this)
    }
}