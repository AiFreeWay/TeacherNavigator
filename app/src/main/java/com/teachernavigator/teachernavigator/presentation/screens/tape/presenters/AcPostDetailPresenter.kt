package com.teachernavigator.teachernavigator.presentation.screens.tape.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.widget.Toast
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.components.DaggerParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.modules.ParentScreenModule
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IPostsInteractor
import com.teachernavigator.teachernavigator.domain.models.Monade
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacade
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.activities.absctraction.PostDetailView
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.abstractions.IPostDetailPresenter
import javax.inject.Inject

/**
 * Created by root on 07.09.17.
 */
class AcPostDetailPresenter : BasePresenter<PostDetailView>(), IPostDetailPresenter {

    @Inject
    lateinit var mPostControllerFacade: IPostControllerFacade
    @Inject
    lateinit var mPostsInteractor: IPostsInteractor

    private var mPostId: Int = -1

    init {
        Logger.logDebug("created PRESENTER AcPostDetailPresenter")
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun attachView(view: PostDetailView) {
        super.attachView(view)
        inject()
    }

    override fun putPostId(postId: Int) {
        mPostId = postId
    }

    override fun loadData() {
        addDissposable(mPostsInteractor.getPostById(mPostId)
                .doOnSubscribe { mView!!.startProgress() }
                .subscribe(this::doOnGetPost, this::doOnError))
    }

    override fun doOnError(error: Throwable) {
        super.doOnError(error)
        mView!!.stopProgress()
        Toast.makeText(mView!!.getActivity(), mView!!.getActivity().getString(R.string.error_throwed), Toast.LENGTH_SHORT).show()
    }

    override fun navigateBack() {
        mView!!.getActivity().finish()
    }

    override fun getPostControllerFacade(): IPostControllerFacade = mPostControllerFacade

    private fun doOnGetPost(post: Post) {
        mView!!.stopProgress()
        mView!!.loadPost(post)
    }

    private fun inject() {
        DaggerParentScreenComponent.builder()
                .rootComponent(getRootComponent(mView!!.getActivity()))
                .parentScreenModule(ParentScreenModule(mView!!))
                .build()
                .inject(this)

    }
}