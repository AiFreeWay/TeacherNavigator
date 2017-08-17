package com.teachernavigator.teachernavigator.presentation.screens.main.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v7.widget.RecyclerView
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.application.di.components.DaggerParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.modules.ParentScreenModule
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IAuthInteractor
import com.teachernavigator.teachernavigator.presentation.menu.MenuController
import com.teachernavigator.teachernavigator.presentation.screens.main.activities.abstractions.MainView
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.TapeFragment
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IAcMainPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by root on 11.08.17.
 */
class AcMainPresenter : IAcMainPresenter() {

    @Inject
    lateinit var mRouter: Router
    @Inject
    lateinit var mAuthInteractor: IAuthInteractor

    private lateinit var mMenuController: MenuController

    init {
        if (BuildConfig.DEBUG) Logger.logDebug("created PRESENTER AcMainPresenter")
    }

    override fun attachView(view: MainView) {
        super.attachView(view)
        inject()
    }

    override fun loadMenuItemsToRecycleView(recylerView: RecyclerView) {
        addDissposable(mAuthInteractor.isAuth()
                .subscribe({doOnGetAuthState(it, recylerView)}, this::doOnError))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        mRouter.replaceScreen(TapeFragment.FRAGMENT_KEY)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun doOnError(error: Throwable) {
        Logger.logError(error)
        mView.stopProgress()
    }

    fun doOnGetAuthState(isAuthorized: Boolean, recylerView: RecyclerView) {
        if (isAuthorized)
            mMenuController = MenuController.createControllerForAuthorizationUser(mView, mView.getActivity())
        else
            mMenuController = MenuController.createControllerForNotAuthorizationUser(mView, mView.getActivity())
        mMenuController.loadToRecycleView(recylerView)

    }

    private fun inject() {
        DaggerParentScreenComponent.builder()
                .rootComponent(getRootComponent(mView.getActivity()))
                .parentScreenModule(ParentScreenModule(mView))
                .build()
                .inject(this)
    }
}