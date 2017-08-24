package com.teachernavigator.teachernavigator.presentation.screens.auth.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.os.Bundle
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.application.di.components.DaggerParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.components.ParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.modules.ParentScreenModule
import com.teachernavigator.teachernavigator.presentation.screens.auth.activities.abstractions.AuthParentView
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.AuthFragment
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.abstractions.IAuthParentPresenter
import com.teachernavigator.teachernavigator.presentation.screens.base.BasePresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by root on 24.08.17.
 */
class AcAuthParentPresenter : BasePresenter<AuthParentView>(), IAuthParentPresenter {

    @Inject
    lateinit var mRouter: Router

    private lateinit var mParentScreenComponent: ParentScreenComponent

    init {
        if (BuildConfig.DEBUG) Logger.logDebug("created PRESENTER AcAuthParentPresenter")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun attachView(view: AuthParentView) {
        super.attachView(view)
        inject()
    }

    override fun doOnError(error: Throwable) {
        super.doOnError(error)
        mView!!.stopProgress()
    }

    override fun openStartFragment(savedState: Bundle?) {
        if (savedState == null)
            mRouter.newRootScreen(AuthFragment.FRAGMENT_KEY)
    }

    override fun getParentScreenComponent(): ParentScreenComponent = mParentScreenComponent


    private fun inject() {
        mParentScreenComponent = DaggerParentScreenComponent.builder()
                .rootComponent(getRootComponent(mView!!.getActivity()))
                .parentScreenModule(ParentScreenModule(mView!!))
                .build()

        mParentScreenComponent.inject(this)
    }
}