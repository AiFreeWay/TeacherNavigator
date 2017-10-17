package com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IJobInteractor
import com.teachernavigator.teachernavigator.presentation.models.ResponseModel
import com.teachernavigator.teachernavigator.presentation.models.VacancyModel
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.abstractions.VacancyView
import com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters.abstractions.IVacancyPresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.ProfileFragment
import com.teachernavigator.teachernavigator.presentation.transformers.VacancyTransformer
import com.teachernavigator.teachernavigator.presentation.transformers.transformEntity
import ru.terrakok.cicerone.Router
import javax.inject.Inject


/**
 * Created by lliepmah on 28.09.17
 */
@PerParentScreen
class VacancyPresenter
@Inject constructor(val router: Router,
                    private val jobsInteractor: IJobInteractor,
                    private val vacancyTransformer: VacancyTransformer) : BasePresenter<VacancyView>(), IVacancyPresenter {

    private var mVacancyId = -1

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        mView?.getParentView()?.setToolbarTitle(R.string.vacancy)
    }

    override fun refresh() {
        mView?.showRefresh()
        loadVacancy(mVacancyId)
    }

    override fun loadVacancy(vacancyId: Int) {
        mVacancyId = vacancyId

        addDissposable(jobsInteractor.loadVacancy(mVacancyId)
                .transformEntity(vacancyTransformer)
                .doOnSubscribe { startProgress() }
                .subscribe(this::onLoaded, this::onError))
    }

    override fun onDownload(response: ResponseModel) = mView?.let {
        if (!response.portfolio.isBlank()) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(response.portfolio))
            startActivity(it.getContext(), intent, null)
        }
    } ?: Unit

    override fun onUser(response: ResponseModel) = mView?.let {
        val bundle = Bundle().apply {
            putInt(ProfileFragment.USER_ID_KEY, response.userId)
            putBoolean(ProfileFragment.IS_MY_PROFILE_KEY, response.isMine)
        }
        router.navigateTo(ProfileFragment.FRAGMENT_KEY, bundle)
    } ?: Unit

    private fun onLoaded(vacancy: VacancyModel) {
        stopProgress()
        mView?.setVacancy(vacancy)
    }

    private fun onError(error: Throwable) {
        stopProgress()
        error.printStackTrace()
        doOnError(error)
    }

    private fun startProgress() {
        mView?.getParentView()?.startProgress()
        mView?.showRefresh()
    }

    private fun stopProgress() {
        mView?.getParentView()?.stopProgress()
        mView?.hideRefresh()
    }


}