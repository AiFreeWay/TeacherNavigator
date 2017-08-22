package com.teachernavigator.teachernavigator.presentation.screens.tape.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.ITapeInteractor
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.screens.base.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.fragments.abstractions.TapeListView
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.abstractions.ITapeListPresenter
import com.teachernavigator.teachernavigator.presentation.utils.TapeStrategy
import javax.inject.Inject

/**
 * Created by root on 18.08.17.
 */
class FmtTapeListPresenter : BasePresenter<TapeListView>(), ITapeListPresenter {

    @Inject
    lateinit var mTapeInteractor: ITapeInteractor

    private var mTapeType: Int = -1

    init {
        if (BuildConfig.DEBUG) Logger.logDebug("created PRESENTER FmtTapeListPresenter")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        getPosts()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun attachView(view: TapeListView) {
        super.attachView(view)
        inject()
    }

    override fun onPostClick(post: Post) {

    }

    override fun doOnError(error: Throwable) {
        super.doOnError(error)
        mView!!.getMainView().stopProgress()
    }

    override fun setTapeType(tapeType: Int) {
        mTapeType = tapeType
    }

    private fun getPosts() {
        TapeStrategy.getPostByType(mTapeType, mTapeInteractor)
                .doOnSubscribe { mView!!.getMainView().startProgress() }
                .subscribe(this::doOnGetPosts, this::doOnError)
    }

    private fun doOnGetPosts(posts: List<Post>) {
        mView!!.getMainView().stopProgress()
        mView!!.loadPosts(posts)
    }

    private fun inject() {
        mView!!.getMainView()
                .getParentScreenComponent()
                .inject(this)
    }
}