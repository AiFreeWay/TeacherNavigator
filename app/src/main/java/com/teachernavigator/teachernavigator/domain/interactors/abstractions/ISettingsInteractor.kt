package com.teachernavigator.teachernavigator.domain.interactors.abstractions

import com.teachernavigator.teachernavigator.domain.models.Settings
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by root on 18.09.17
 */
interface ISettingsInteractor {

    fun getSettings(): Observable<Settings>
    fun putSettings(settings: Settings)
    fun updateFCM()
}