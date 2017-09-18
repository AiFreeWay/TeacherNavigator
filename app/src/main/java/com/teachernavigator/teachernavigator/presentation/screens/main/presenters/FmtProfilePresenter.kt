package com.teachernavigator.teachernavigator.presentation.screens.main.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.widget.Toast
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IProfileInteractor
import com.teachernavigator.teachernavigator.domain.models.Profile
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.ProfileView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IProfilePresenter
import javax.inject.Inject

/**
 * Created by root on 18.09.17.
 */
class FmtProfilePresenter : BasePresenter<ProfileView>(), IProfilePresenter {

    @Inject
    lateinit var mProfileInteractor: IProfileInteractor

    init {
        Logger.logDebug("created PRESENTER FmtProfilePresenter")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        mView!!.getParentView().setToolbarTitle(R.string.profile)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun attachView(view: ProfileView) {
        super.attachView(view)
        inject()
    }

    override fun doOnError(error: Throwable) {
        super.doOnError(error)
        mView!!.getParentView().stopProgress()
        Toast.makeText(mView!!.getContext(), mView!!.getContext().getString(R.string.error_throwed), Toast.LENGTH_SHORT).show()
    }

    override fun getProfile() {
        addDissposable(mProfileInteractor.getProfile()
                .doOnSubscribe { mView!!.getParentView().startProgress() }
                .subscribe(this::doOnGetProfile, this::doOnError))
    }

    private fun doOnGetProfile(profile: Profile) {
        mView!!.getParentView().stopProgress()
        mView!!.loadProfile(profile)
    }

    private fun inject() {
        mView!!.getParentView()
                .getParentScreenComponent()
                .inject(this)
    }
}