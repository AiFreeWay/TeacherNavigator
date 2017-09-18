package com.teachernavigator.teachernavigator.domain.interactors

import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.ISettingsInteractor
import com.teachernavigator.teachernavigator.data.repository.abstractions.ISettingsRepository
import com.teachernavigator.teachernavigator.domain.models.Settings
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by root on 18.09.17.
 */
class SettingsInteractor @Inject constructor(private val mRepository: ISettingsRepository) : ISettingsInteractor {

    init {
        Logger.logDebug("created INTERACTOR SettingsInteractor")
    }

    override fun getSettings(): Observable<Settings> = mRepository.getSettings()

    override fun putSettings(settings: Settings) {
        mRepository.putSettings(settings)
    }
}