package com.teachernavigator.teachernavigator.presentation.screens.auth.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.os.Bundle
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.presentation.screens.auth.activities.abstractions.AuthParentView
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.AuthFragment
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.abstractions.IAuthParentPresenter
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by root on 24.08.17
 */
@PerParentScreen
class AuthParentPresenter
@Inject
constructor(val router: Router) : BasePresenter<AuthParentView>(), IAuthParentPresenter {

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun doOnError(error: Throwable) {
        super.doOnError(error)
        mView!!.stopProgress()
    }

    override fun openStartFragment(savedState: Bundle?) {
        if (savedState == null) {
            router.newRootScreen(AuthFragment.FRAGMENT_KEY)
        }
    }

    override fun navigateBack() = router.exit()

}