package com.teachernavigator.teachernavigator.presentation.screens.tape.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.application.di.components.DaggerParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.components.ParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.modules.ParentScreenModule
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IPostsInteractor
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.screens.base.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.activities.absctraction.PostDetailView
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.abstractions.IPostDetailPresenter
import javax.inject.Inject

/**
 * Created by root on 07.09.17.
 */
class AcPostDetailPresenter : BasePresenter<PostDetailView>(), IPostDetailPresenter {

    private lateinit var mParentScreenComponent: ParentScreenComponent
    private var mPostId: Int = -1

    @Inject
    lateinit var mPostInteractor: IPostsInteractor

    init {
        if (BuildConfig.DEBUG) Logger.logDebug("created PRESENTER AcPostDetailPresenter")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        getPost()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun attachView(view: PostDetailView) {
        super.attachView(view)
        inject()
    }

    override fun doOnError(error: Throwable) {
        super.doOnError(error)
        mView!!.stopProgress()
    }

    override fun navigateBack() {
        mView!!.getActivity().finish()
    }

    override fun putPostId(postId: Int) {
        mPostId = postId
    }

    private fun getPost() {
            mPostInteractor.getPostById(mPostId)
                .doOnSubscribe { mView!!.startProgress()}
                .subscribe(this::doOnGetPost, this::doOnError)
    }

    private fun doOnGetPost(post: Post) {
        mView!!.stopProgress()
        mView!!.loadPost(post)
    }

    private fun inject() {
        mParentScreenComponent = DaggerParentScreenComponent.builder()
                .rootComponent(getRootComponent(mView!!.getActivity()))
                .parentScreenModule(ParentScreenModule(mView!!))
                .build()

        mParentScreenComponent.inject(this)
    }
}