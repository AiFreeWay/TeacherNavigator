package com.teachernavigator.teachernavigator.presentation.screens.settings.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.text.TextUtils
import android.widget.Toast
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.components.DaggerParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.modules.ParentScreenModule
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.ISettingsInteractor
import com.teachernavigator.teachernavigator.domain.models.Settings
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.settings.fragments.abstractions.AppSettingsView
import com.teachernavigator.teachernavigator.presentation.screens.settings.presenters.abstractions.IAppSettingsPresenter
import javax.inject.Inject

/**
 * Created by root on 18.09.17.
 */
class FmtAppSettingsPresenter : BasePresenter<AppSettingsView>(), IAppSettingsPresenter {

    @Inject
    lateinit var mSettingsInteractor: ISettingsInteractor

    init {
        Logger.logDebug("created PRESENTER FmtAppSettingsPresenter")
    }

    private var mSettings = Settings()

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun attachView(view: AppSettingsView) {
        super.attachView(view)
        inject()
    }

    override fun getSettings() {
        mDisposables.add(mSettingsInteractor.getSettings()
                .doOnSubscribe { this::doOnSubscribe }
                .subscribe(this::doOnGetSettings, this::doOnError))
    }

    override fun changeNightTheme(state: Boolean) {
        mSettings.isNithThemeOn = state
        mSettingsInteractor.putSettings(mSettings)
    }

    override fun changePush(state: Boolean) {
        mSettings.isPushOn = state
        mSettingsInteractor.putSettings(mSettings)
    }

    override fun changeSound(state: Boolean) {
        mSettings.isSoundOn = state
        mSettingsInteractor.putSettings(mSettings)
    }

    override fun doOnError(error: Throwable) {
        super.doOnError(error)
        mView!!.getParentView().stopProgress()
        mView!!.unlockUi()
        Toast.makeText(mView!!.getContext(), mView!!.getContext().getString(R.string.error_throwed), Toast.LENGTH_SHORT).show()
    }

    private fun doOnGetSettings(settings: Settings) {
        mSettings = settings
        mView!!.getParentView().stopProgress()
        mView!!.unlockUi()
        mView!!.loadSettings(settings)
    }

    private fun doOnSubscribe() {
        mView!!.getParentView().startProgress()
        mView!!.lockUi()
    }

    private fun inject() {
        mView!!.getParentView()
                .getParentScreenComponent()
                .inject(this)
    }
}