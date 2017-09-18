package com.teachernavigator.teachernavigator.domain.interactors.abstractions

import com.teachernavigator.teachernavigator.domain.models.Profile
import io.reactivex.Observable

/**
 * Created by root on 18.09.17.
 */
interface IProfileInteractor {

    fun getProfile(): Observable<Profile>
}