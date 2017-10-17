package com.teachernavigator.teachernavigator.presentation.screens.main.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v4.app.Fragment
import android.util.Log.d
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IAuthInteractor
import com.teachernavigator.teachernavigator.presentation.factories.PostsFragmentsFactory
import com.teachernavigator.teachernavigator.presentation.models.PostsSource
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.TapeView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.ITapePresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.fragments.PostsListFragment
import com.teachernavigator.teachernavigator.presentation.screens.tape.fragments.PostsSearchFragment
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by root on 17.08.17
 */
@PerParentScreen
class TapePresenter
@Inject constructor(private val router: Router,
                    private val authInteractor: IAuthInteractor) : BasePresenter<TapeView>(), ITapePresenter {

    private var mType = PostsSource.Common

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        val title = when (mType) {
            PostsSource.Common -> R.string.news_feed
            PostsSource.Mine -> R.string.my_publication
            PostsSource.Saved -> R.string.saved
            PostsSource.Best -> R.string.news_feed
        }
        mView?.getParentView()?.setToolbarTitle(title)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun doOnError(error: Throwable) {
        super.doOnError(error)
        mView?.getParentView()?.stopProgress()
    }

    override fun loadFragments(type: Int?) {
        mType = type?.let { PostsSource.values().getOrNull(it) } ?: PostsSource.Common

        addDissposable(authInteractor.isAuthAsync()
                .doOnSubscribe { mView!!.getParentView().startProgress() }
                .subscribe({ doOnGetIsUserAuthInfo(it) }, this::doOnError))
    }

    override fun openPostSearchScreen() {
        router.navigateTo(PostsSearchFragment.FRAGMENT_KEY)
    }

    override fun refresh(fragment: Fragment?) {
        if (fragment != null) {
            (fragment as PostsListFragment).refresh()
        }
    }

    private fun doOnGetIsUserAuthInfo(isAuth: Boolean) {
        mView?.getParentView()?.stopProgress()

        val fragmentsContainer = when {
            isAuth && mType == PostsSource.Common -> PostsFragmentsFactory.createItems(getContext())
            !isAuth && mType == PostsSource.Common -> PostsFragmentsFactory.createForUnregisterUserItems(getContext())
            else -> PostsFragmentsFactory.createMyItems(getContext(), mType)
        }

        mView?.loadOrdersFragments(fragmentsContainer)
    }

}