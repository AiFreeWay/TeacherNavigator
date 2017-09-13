package com.teachernavigator.teachernavigator.presentation.screens.tape.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.widget.Toast
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IPostsInteractor
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacade
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.fragments.abstractions.PostsListView
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.abstractions.IPostsListPresenter
import com.teachernavigator.teachernavigator.presentation.utils.TapeStrategy
import javax.inject.Inject

/**
 * Created by root on 18.08.17.
 */
class FmtPostsListPresenter : BasePresenter<PostsListView>(), IPostsListPresenter {

    @Inject
    lateinit var mPostInteractor: IPostsInteractor
    @Inject
    lateinit var mPostControllerFacade: IPostControllerFacade

    private var mTapeType: Int = -1

    init {
        Logger.logDebug("created PRESENTER FmtPostsListPresenter")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        getPosts()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun attachView(view: PostsListView) {
        super.attachView(view)
        inject()
    }

    override fun doOnError(error: Throwable) {
        super.doOnError(error)
        mView!!.getParentView().stopProgress()
        mView!!.showNoDataText()
        Toast.makeText(mView!!.getContext(), mView!!.getContext().getString(R.string.error_throwed), Toast.LENGTH_SHORT).show()
    }

    override fun setTapeType(tapeType: Int) {
        mTapeType = tapeType
    }

    override fun getPostControllerFacade(): IPostControllerFacade = mPostControllerFacade

    override fun getPosts() {
        addDissposable(TapeStrategy.getPostByType(mTapeType, mPostInteractor)
                .doOnSubscribe { this::doOnSubscribeOnGetPosts }
                .subscribe(this::doOnGetPosts, this::doOnError))
    }

    private fun doOnGetPosts(posts: List<Post>) {
        mView!!.getParentView().stopProgress()
        mView!!.loadPosts(posts)

        if (posts.isNotEmpty())
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