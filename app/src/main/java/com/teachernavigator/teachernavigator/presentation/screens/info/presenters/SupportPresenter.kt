package com.teachernavigator.teachernavigator.presentation.screens.info.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.info.fragments.abstractions.SupportView
import com.teachernavigator.teachernavigator.presentation.screens.info.presenters.abstractions.ISupportPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by lliepmah on 05.10.17
 */
@PerParentScreen
class SupportPresenter
@Inject constructor(val router: Router) : BasePresenter<SupportView>(), ISupportPresenter {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() =
            mView?.getParentView()?.setToolbarTitle(R.string.support)


}