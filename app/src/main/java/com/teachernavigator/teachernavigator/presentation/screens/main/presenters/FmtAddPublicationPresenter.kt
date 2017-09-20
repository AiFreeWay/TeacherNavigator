package com.teachernavigator.teachernavigator.presentation.screens.main.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.activities.PreviewPostActivity
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.AddPublicationView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IAddPublicationPresenter
import com.teachernavigator.teachernavigator.presentation.utils.ActivityRouter

/**
 * Created by root on 20.09.17.
 */
class FmtAddPublicationPresenter : BasePresenter<AddPublicationView>(), IAddPublicationPresenter {

    init {
        Logger.logDebug("created PRESENTER FmtAddPublicationPresenter")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        mView!!.getParentView().setToolbarTitle(R.string.add_publication)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun attachView(view: AddPublicationView) {
        super.attachView(view)
        inject()
    }

    override fun doOnError(error: Throwable) {
        super.doOnError(error)
        mView!!.getParentView().stopProgress()
        Toast.makeText(mView!!.getContext(), mView!!.getContext().getString(R.string.error_throwed), Toast.LENGTH_SHORT).show()
    }

    override fun doPublic(post: Post) {
        if (TextUtils.isEmpty(post.title) || TextUtils.isEmpty(post.text))
            return
    }

    override fun preview(post: Post) {
        if (TextUtils.isEmpty(post.title) || TextUtils.isEmpty(post.text))
            return
        val bundle = Bundle()
        bundle.putSerializable(PreviewPostActivity.POST_KEY, post)
        ActivityRouter.openActivity(mView!!.getParentView().getActivity(), bundle, PreviewPostActivity::class.java)
    }

    private fun inject() {
        mView!!.getParentView()
                .getParentScreenComponent()
                .inject(this)
    }
}