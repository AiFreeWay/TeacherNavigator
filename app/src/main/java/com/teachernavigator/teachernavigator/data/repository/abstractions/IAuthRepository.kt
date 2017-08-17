package com.teachernavigator.teachernavigator.data.repository.abstractions

import io.reactivex.Observable

/**
 * Created by root on 11.08.17.
 */
interface IAuthRepository {


    fun isAuth(): Observable<Boolean>
}