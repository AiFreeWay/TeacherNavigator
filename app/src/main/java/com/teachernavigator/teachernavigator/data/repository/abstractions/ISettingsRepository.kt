package com.teachernavigator.teachernavigator.data.repository.abstractions

import com.teachernavigator.teachernavigator.domain.models.Settings
import io.reactivex.Observable

/**
 * Created by root on 18.09.17.
 */
interface ISettingsRepository {

    fun getSettings(): Observable<Settings>
    fun putSettings(settings: Settings)
}