package com.teachernavigator.teachernavigator.presentation.ui.main.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.application.di.components.DaggerTopFragmentContainerComponent
import com.teachernavigator.teachernavigator.application.di.modules.TopFragmentContainerModule
import com.teachernavigator.teachernavigator.presentation.ui.main.activities.abstractions.MainView
import com.teachernavigator.teachernavigator.presentation.ui.main.fragments.TapeFragment
import com.teachernavigator.teachernavigator.presentation.ui.main.presenters.abstractions.IAcMainPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by root on 11.08.17.
 */
class AcMainPresenter(view: MainView) : IAcMainPresenter(view) {

    @Inject
    lateinit var mRouter: Router

    init {
        if (BuildConfig.DEBUG) Logger.testLog("created PRESENTER AcMainPresenter")
        inject()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        mRouter.replaceScreen(TapeFragment.FRAGMENT_KEY)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    private fun inject() {
        DaggerTopFragmentContainerComponent.builder()
                .rootComponent(getRootComponent(mView.getActivity()))
                .topFragmentContainerModule(TopFragmentContainerModule(mView))
                .build()
                .inject(this)
    }
}