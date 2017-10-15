package com.teachernavigator.teachernavigator.presentation.screens.main.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.widget.Toast
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IPostsInteractor
import com.teachernavigator.teachernavigator.domain.models.PostType
import com.teachernavigator.teachernavigator.presentation.models.PostModel
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.MyPublicationsView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IMyPublicationsPresenter
import com.teachernavigator.teachernavigator.presentation.transformers.PostTransformerFactory
import com.teachernavigator.teachernavigator.presentation.transformers.transformListEntity
import javax.inject.Inject

/**
 * Created by root on 13.09.17
 */
class MyPublicationsPresenter : BasePresenter<MyPublicationsView>(), IMyPublicationsPresenter {

    @Inject
    lateinit var mPostInteractor: IPostsInteractor
    @Inject
    lateinit var postTransformerFactory: PostTransformerFactory

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        mView!!.getParentView().setToolbarTitle(R.string.my_publication)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun doOnError(error: Throwable) {
        super.doOnError(error)
        mView!!.getParentView().stopProgress()
        mView!!.showNoDataText()
        Toast.makeText(mView!!.getContext(), mView!!.getContext().getString(R.string.error_throwed), Toast.LENGTH_SHORT).show()
    }

    override fun getMyPublications() {
        addDissposable(mPostInteractor.getMyPublications()
                .transformListEntity(postTransformerFactory.build(PostType.post, true))
                .doOnSubscribe { (this::doOnSubscribeOnGetPosts)() }
                .subscribe(this::doOnGetMyPublications, this::doOnError))
    }

    override fun refresh() {
        getMyPublications()
    }

    private fun doOnGetMyPublications(posts: List<PostModel>) {
        mView!!.getParentView().stopProgress()
        mView!!.loadMyPublications(posts)

        if (posts.isNotEmpty())
            mView!!.hideNoDataText()
        else
            mView!!.showNoDataText()
    }

    private fun doOnSubscribeOnGetPosts() {
        mView!!.getParentView().startProgress()
        mView!!.hideNoDataText()
    }

}