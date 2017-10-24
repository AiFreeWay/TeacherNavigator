package com.teachernavigator.teachernavigator.data.repository.abstractions

import com.teachernavigator.teachernavigator.data.network.requests.ConvertTokenRequest
import com.teachernavigator.teachernavigator.data.network.requests.RestorePasswordRequest
import com.teachernavigator.teachernavigator.data.network.requests.SingInRequest
import com.teachernavigator.teachernavigator.data.network.requests.SingUpRequest
import com.teachernavigator.teachernavigator.data.network.responses.BaseResponse
import com.teachernavigator.teachernavigator.data.network.responses.SingInResponse
import com.teachernavigator.teachernavigator.domain.models.AuthCredentials
import com.teachernavigator.teachernavigator.domain.models.Monade
import com.teachernavigator.teachernavigator.domain.models.Token
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by root on 11.08.17
 */
interface IAuthRepository {

    fun getTokenAsync(): Single<Token>
    fun isAuth(): Boolean
    fun saveToken(token: Token)
    fun singInViaSocials(request: ConvertTokenRequest): Single<SingInResponse>
    fun singInViaTwitter(): Observable<Monade>
    fun singIn(request: SingInRequest): Single<SingInResponse>
    fun singUp(request: SingUpRequest): Single<BaseResponse>
    fun restorePassword(request: RestorePasswordRequest): Single<BaseResponse>
    fun getAuthCredentials(): AuthCredentials

    fun updateFCMToken()
}