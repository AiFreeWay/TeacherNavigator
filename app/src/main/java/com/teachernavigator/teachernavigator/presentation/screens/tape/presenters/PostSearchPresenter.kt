package com.teachernavigator.teachernavigator.presentation.screens.tape.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IPostsInteractor
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.fragments.abstractions.PostsSearchView
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.abstractions.IPostSearchPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by root on 06.09.17
 */
@PerParentScreen
class PostSearchPresenter
@Inject constructor(val router: Router,
                    private val postsInteractor: IPostsInteractor) : BasePresenter<PostsSearchView>(), IPostSearchPresenter {


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() =
            mView?.getParentView()?.setToolbarTitle(R.string.search_publication)

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun doOnError(error: Throwable) {
        super.doOnError(error)
        stopProgress()
    }

    override fun navigateBack() {
        router.exit()
    }

    private fun startProgress() {
        mView?.getParentView()?.startProgress()
    }

    private fun stopProgress() {
        mView?.getParentView()?.stopProgress()
    }

}