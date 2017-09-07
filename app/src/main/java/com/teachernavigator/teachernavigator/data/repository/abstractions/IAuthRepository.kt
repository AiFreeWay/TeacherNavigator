package com.teachernavigator.teachernavigator.data.repository.abstractions

import com.teachernavigator.teachernavigator.data.network.requests.RestorePasswordRequest
import com.teachernavigator.teachernavigator.data.network.requests.SingInRequest
import com.teachernavigator.teachernavigator.data.network.requests.SingUpRequest
import com.teachernavigator.teachernavigator.data.network.responses.BaseResponse
import com.teachernavigator.teachernavigator.data.network.responses.SingInResponse
import com.teachernavigator.teachernavigator.domain.models.AuthCredentials
import com.teachernavigator.teachernavigator.domain.models.Monade
import com.teachernavigator.teachernavigator.domain.models.Token
import io.reactivex.Observable

/**
 * Created by root on 11.08.17.
 */
interface IAuthRepository {

    fun getToken(): Observable<Token>
    fun saveToken(token: Token)
    fun singInViaVkontakte(): Observable<Monade>
    fun singInViaFacebook(): Observable<Monade>
    fun singInViaTwitter(): Observable<Monade>
    fun singInViaGooglePlus(): Observable<Monade>
    fun singIn(request: SingInRequest): Observable<SingInResponse>
    fun singUp(request: SingUpRequest): Observable<BaseResponse>
    fun restorePassword(request: RestorePasswordRequest): Observable<BaseResponse>
    fun getAuthCredentials(): AuthCredentials
}