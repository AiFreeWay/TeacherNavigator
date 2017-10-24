package com.teachernavigator.teachernavigator.domain.interactors.abstractions

import com.teachernavigator.teachernavigator.domain.models.Monade
import com.teachernavigator.teachernavigator.domain.models.SingUpData
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by root on 17.08.17
 */
interface IAuthInteractor {

    fun isAuthAsync(): Single<Boolean>

    fun singInViaGoogle(token: String): Single<Monade>
    fun singInViaVk(token: String): Single<Monade>
    fun singInViaFacebook(token: String): Single<Monade>
    fun singInViaTwitter(token: String): Single<Monade>

    fun singIn(login: String, password: String): Single<Monade>
    fun singUp(singUpData: SingUpData): Single<Monade>

    fun restorePassword(email: String, phone: String): Single<Monade>

    fun updateFCMToken()
}