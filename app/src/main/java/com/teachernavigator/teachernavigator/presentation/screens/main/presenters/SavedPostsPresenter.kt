package com.teachernavigator.teachernavigator.presentation.screens.main.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.widget.Toast
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IPostsInteractor
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacade
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.SavedPostsView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.ISavedPostsPresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.fragments.PostsSearchFragment
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by root on 08.09.17
 */
@PerParentScreen
class SavedPostsPresenter
@Inject constructor(val router: Router,
                    private val postsInteractor: IPostsInteractor,
                    private val postControllerFacade: IPostControllerFacade) : BasePresenter<SavedPostsView>(), ISavedPostsPresenter {

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
        mView?.getParentView()?.stopProgress()
        mView?.showNoDataText()
        Toast.makeText(mView!!.getContext(), mView!!.getContext().getString(R.string.error_throwed), Toast.LENGTH_SHORT).show()
    }

    override fun getPostControllerFacade(): IPostControllerFacade = postControllerFacade

    override fun getSavedPosts() {
        addDissposable(postsInteractor.getSavedPosts()
                .doOnSubscribe { doOnSubscribeOnGetPosts() }
                .subscribe(this::doOnGetSavedPosts, this::doOnError))
    }

    override fun openPostSearchScreen() {
        router.navigateTo(PostsSearchFragment.FRAGMENT_KEY)
    }

    override fun refresh() {
        getSavedPosts()
    }

    private fun doOnGetSavedPosts(posts: List<Post>) {
        mView!!.getParentView().stopProgress()
        mView!!.loadSavedPosts(posts)

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