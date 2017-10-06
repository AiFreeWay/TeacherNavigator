package com.teachernavigator.teachernavigator.presentation.screens.info.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.os.Bundle
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.presentation.models.Specialist
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.info.fragments.AskSpecialistFragment
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

    override fun openLabourProtection() =
            openAskSpecialist(Specialist.occupational_safety_specialist.ordinal)

    override fun openMethodist() =
            openAskSpecialist(Specialist.metodist.ordinal)

    override fun openPsychologist() =
            openAskSpecialist(Specialist.psychologist.ordinal)

    override fun openLegalInspector() =
            openAskSpecialist(Specialist.legal_inspector.ordinal)

    override fun openEconomist() =
            openAskSpecialist(Specialist.economist.ordinal)


    private fun openAskSpecialist(ordinal: Int) {
        val bundle = Bundle()
        bundle.putInt(AskSpecialistFragment.SPECIALIST_KEY, ordinal)
        router.navigateTo(AskSpecialistFragment.FRAGMENT_KEY, bundle)
    }

}