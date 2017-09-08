package com.teachernavigator.teachernavigator.presentation.screens.tape.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.application.di.components.DaggerParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.modules.ParentScreenModule
import com.teachernavigator.teachernavigator.presentation.screens.base.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.activities.absctraction.PostSearchView
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.abstractions.IPostSearchPresenter

/**
 * Created by root on 06.09.17.
 */
class AcPostSearchPresenter : BasePresenter<PostSearchView>(), IPostSearchPresenter {

    init {
        Logger.logDebug("created PRESENTER AcPostSearchPresenter")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun attachView(view: PostSearchView) {
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

    private fun inject() {
        DaggerParentScreenComponent.builder()
                .rootComponent(getRootComponent(mView!!.getActivity()))
                .parentScreenModule(ParentScreenModule(mView!!))
                .build()
                .inject(this)
    }
}