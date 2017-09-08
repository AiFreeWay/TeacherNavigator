package com.teachernavigator.teachernavigator.presentation.screens.main.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.widget.Toast
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IPostsInteractor
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacade
import com.teachernavigator.teachernavigator.presentation.screens.base.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.SavedPostsView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.ISavedPostsPresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.activities.PostSearchActivity
import com.teachernavigator.teachernavigator.presentation.utils.ActivityRouter
import javax.inject.Inject

/**
 * Created by root on 08.09.17.
 */
class FmtSavedPostsPresenter : BasePresenter<SavedPostsView>(), ISavedPostsPresenter {

    @Inject
    lateinit var mPostInteractor: IPostsInteractor
    @Inject
    lateinit var mPostControllerFacade: IPostControllerFacade

    init {
        Logger.logDebug("created PRESENTER FmtSavedPostsPresenter")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        mView!!.getParentView().setToolbarTitle(R.string.saved)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun attachView(view: SavedPostsView) {
        super.attachView(view)
        inject()
    }

    override fun doOnError(error: Throwable) {
        super.doOnError(error)
        mView!!.getParentView().stopProgress()
        mView!!.showNoDataText()
        Toast.makeText(mView!!.getContext(), mView!!.getContext().getString(R.string.error_throwed), Toast.LENGTH_SHORT).show()
    }

    override fun getPostControllerFacade(): IPostControllerFacade = mPostControllerFacade

    override fun getPosts() {
        addDissposable(mPostInteractor.getSavedPosts()
                .doOnSubscribe { this::doOnSubscribeOnGetPosts }
                .subscribe(this::doOnGetPosts, this::doOnError))
    }

    override fun openPostSearchScreen() {
        ActivityRouter.openActivity(mView!!.getParentView().getActivity(), PostSearchActivity::class.java)
    }

    override fun refresh() {
        getPosts()
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