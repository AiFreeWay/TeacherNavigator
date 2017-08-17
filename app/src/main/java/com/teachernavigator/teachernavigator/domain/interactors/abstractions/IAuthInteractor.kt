package com.teachernavigator.teachernavigator.domain.interactors.abstractions

import io.reactivex.Observable

/**
 * Created by root on 17.08.17.
 */
interface IAuthInteractor {

    fun isAuth(): Observable<Boolean>
}