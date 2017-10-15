package com.teachernavigator.teachernavigator.presentation.screens.main.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v4.app.Fragment
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IAuthInteractor
import com.teachernavigator.teachernavigator.presentation.factories.PostsFragmentsFactory
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

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        mView?.getParentView()?.setToolbarTitle(R.string.tape)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun doOnError(error: Throwable) {
        super.doOnError(error)
        mView?.getParentView()?.stopProgress()
    }

    override fun loadFragments() {
        addDissposable(authInteractor.isAuthAsynch()
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

        val fragmentsContainer = if (isAuth) {
            PostsFragmentsFactory.createItems(getContext())
        } else {
            PostsFragmentsFactory.createForUnregisterUserItems(getContext())
        }

        mView?.loadOrdersFragments(fragmentsContainer)
    }

}