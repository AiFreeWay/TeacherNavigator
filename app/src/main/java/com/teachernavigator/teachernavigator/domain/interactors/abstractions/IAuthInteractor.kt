package com.teachernavigator.teachernavigator.domain.interactors.abstractions

import com.teachernavigator.teachernavigator.data.network.responses.SingInResponse
import com.teachernavigator.teachernavigator.domain.models.Monade
import com.teachernavigator.teachernavigator.domain.models.SingUpData
import com.teachernavigator.teachernavigator.domain.models.Token
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by root on 17.08.17
 */
interface IAuthInteractor {

    fun isAuthAsynch(): Observable<Boolean>

    fun singInViaVkontakte(): Observable<Monade>
    fun singInViaTwitter(token: String): Single<Monade>
    fun singInViaFacebook(token: String): Single<Monade>
    fun singInViaGooglePlus(): Observable<Monade>
    fun singIn(login: String, password: String): Observable<Monade>


    fun singUp(singUpData: SingUpData): Observable<Monade>

    fun restorePassword(login: String): Observable<Monade>
}