package com.teachernavigator.teachernavigator.domain.interactors.abstractions

import com.teachernavigator.teachernavigator.domain.models.Monade
import com.teachernavigator.teachernavigator.domain.models.SingUpData
import io.reactivex.Observable

/**
 * Created by root on 17.08.17.
 */
interface IAuthInteractor {

    fun isAuth(): Observable<Boolean>

    fun singInViaVkontakte(): Observable<Monade>
    fun singInViaFacebook(): Observable<Monade>
    fun singInViaTwitter(): Observable<Monade>
    fun singInViaGooglePlus(): Observable<Monade>
    fun singIn(login: String, password: String): Observable<Monade>

    fun singUp(singUpData: SingUpData): Observable<Monade>

    fun restorePassword(login: String): Observable<Monade>
}