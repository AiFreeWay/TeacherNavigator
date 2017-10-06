package com.teachernavigator.teachernavigator.presentation.screens.info.presenters

import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IProfileInteractor
import com.teachernavigator.teachernavigator.presentation.models.Specialist
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.info.fragments.abstractions.AskSpecialistView
import com.teachernavigator.teachernavigator.presentation.screens.info.presenters.abstractions.IAskSpecialistPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by lliepmah on 05.10.17
 */
@PerParentScreen
class AskSpecialistPresenter
@Inject constructor(val router: Router,
                    private val interactor: IProfileInteractor) : BasePresenter<AskSpecialistView>(), IAskSpecialistPresenter {

    override var specialist: Specialist? = null
        set(value) {
            field = value
            if (value != null) {
                mView?.getParentView()?.setToolbarTitle(value.nameResId)
            }
        }

    override fun askQuestion(message: String) {
        val spec = specialist
        if (!message.isBlank() && spec != null) {
            addDissposable(interactor.askSpecialist(spec, message)
                    .doOnSubscribe { startProgress() }
                    .subscribe(this::onLoaded, this::doOnError))
        }
    }

    private fun onLoaded(stub: Unit) {
        stopProgress()
        mView?.cleanField()
    }

    private fun startProgress() =
            mView?.getParentView()?.startProgress()

    private fun stopProgress() =
            mView?.getParentView()?.stopProgress()


}