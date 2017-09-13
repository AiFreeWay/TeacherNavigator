package com.teachernavigator.teachernavigator.presentation.screens.main.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v4.app.Fragment
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IAuthInteractor
import com.teachernavigator.teachernavigator.presentation.factories.PostsFragmentsFactory
import com.teachernavigator.teachernavigator.presentation.models.ViewPagerItemContainer
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.TapeView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.ITapePresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.activities.PostSearchActivity
import com.teachernavigator.teachernavigator.presentation.screens.tape.fragments.PostsListFragment
import com.teachernavigator.teachernavigator.presentation.utils.ActivityRouter
import javax.inject.Inject

/**
 * Created by root on 17.08.17.
 */
class FmtTapePresenter : BasePresenter<TapeView>(), ITapePresenter {

    @Inject
    lateinit var mAuthInteractor: IAuthInteractor

    init {
        Logger.logDebug("created PRESENTER FmtTapePresenter")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        mView!!.getParentView().setToolbarTitle(R.string.tape)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun attachView(view: TapeView) {
        super.attachView(view)
        inject()
    }

    override fun doOnError(error: Throwable) {
        super.doOnError(error)
        mView!!.getParentView().stopProgress()
    }

    override fun loadFragments() {
        addDissposable(mAuthInteractor.isAuthAsynch()
                .doOnSubscribe { mView!!.getParentView().startProgress() }
                .subscribe({ doOnGetIsUserAuthInfo(it)}, this::doOnError))
    }

    override fun openPostSearchScreen() {
        ActivityRouter.openActivity(mView!!.getParentView().getActivity(), PostSearchActivity::class.java)
    }

    override fun refresh(fragment: Fragment?) {
        if (fragment != null) {
            (fragment as PostsListFragment).refresh()
        }
    }

    private fun doOnGetIsUserAuthInfo(isAuth: Boolean) {
        mView!!.getParentView().stopProgress()
        val fragmentsContainer: List<ViewPagerItemContainer>
        if (isAuth)
            fragmentsContainer = PostsFragmentsFactory.createItems(getContext())
        else
            fragmentsContainer = PostsFragmentsFactory.createForUnregisterUserItems(getContext())
        mView!!.loadOrdersFragments(fragmentsContainer)
    }

    private fun inject() {
        mView!!.getParentView()
                .getParentScreenComponent()
                .inject(this)
    }
}