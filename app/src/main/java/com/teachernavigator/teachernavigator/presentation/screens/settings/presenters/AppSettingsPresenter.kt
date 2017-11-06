package com.teachernavigator.teachernavigator.presentation.screens.settings.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.widget.Toast
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.ISettingsInteractor
import com.teachernavigator.teachernavigator.domain.models.Settings
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.settings.fragments.abstractions.AppSettingsView
import com.teachernavigator.teachernavigator.presentation.screens.settings.presenters.abstractions.IAppSettingsPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by root on 18.09.17
 */
@PerParentScreen
class AppSettingsPresenter
@Inject
constructor(val router: Router,
            private val settingsInteractor: ISettingsInteractor) : BasePresenter<AppSettingsView>(), IAppSettingsPresenter {

    override fun applySettings() {
        settingsInteractor.putSettings(mSettings)
        settingsInteractor.updateFCM()
        mView?.getParentView()?.getActivity()?.recreate()
    }

    private var mSettings = Settings()

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun getSettings() {
        mDisposables.add(settingsInteractor.getSettings()
                .doOnSubscribe { doOnSubscribe() }
                .subscribe(this::doOnGetSettings, this::doOnError))
    }

    override fun setFont(value: Int) {
        mSettings.fontType = value
        mView?.setSettings(mSettings)
    }


    override fun changeNightTheme(state: Boolean) {
        mSettings.night = state
        mView?.setSettings(mSettings)
    }

    override fun changePush(state: Boolean) {
        mSettings.isPushOn = state
        settingsInteractor.putSettings(mSettings)
    }

    override fun changeSound(state: Boolean) {
        mSettings.isSoundOn = state
        settingsInteractor.putSettings(mSettings)
    }

    override fun doOnError(error: Throwable) {
        super.doOnError(error)
        mView?.getParentView()?.stopProgress()
        mView?.unlockUi()
        Toast.makeText(mView!!.getContext(), mView!!.getContext().getString(R.string.error_throwed), Toast.LENGTH_SHORT).show()
    }

    private fun doOnGetSettings(settings: Settings) {
        mSettings = settings
        mView?.getParentView()?.stopProgress()
        mView?.unlockUi()
        mView?.setSettings(settings)
    }

    private fun doOnSubscribe() {
        mView?.getParentView()?.startProgress()
        mView?.lockUi()
    }

}