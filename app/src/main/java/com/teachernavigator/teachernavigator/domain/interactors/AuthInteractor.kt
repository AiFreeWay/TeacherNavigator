package com.teachernavigator.teachernavigator.domain.interactors

import com.teachernavigator.teachernavigator.data.network.requests.RestorePasswordRequest
import com.teachernavigator.teachernavigator.data.network.responses.SingInResponse
import com.teachernavigator.teachernavigator.data.repository.abstractions.IAuthRepository
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IAuthInteractor
import com.teachernavigator.teachernavigator.domain.mappers.AuthMapper
import com.teachernavigator.teachernavigator.domain.mappers.BaseMapper
import com.teachernavigator.teachernavigator.domain.models.Monade
import com.teachernavigator.teachernavigator.domain.models.SingUpData
import com.teachernavigator.teachernavigator.presentation.utils.applySchedulers
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by root on 17.08.17
 */
class AuthInteractor @Inject constructor(private val mRepository: IAuthRepository) : IAuthInteractor {

    override fun isAuthAsync(): Single<Boolean> =
            mRepository.getTokenAsync()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .map { it.isExists() }

    override fun singInViaFacebook(token: String): Single<Monade> =
            mRepository.singInViaFacebook(AuthMapper.mapConvertTokenRequest(token, "facebook", mRepository.getAuthCredentials()))
                    .map { mapSingInResponse(it) }
                    .applySchedulers()

    override fun singInViaGoogle(token: String): Single<Monade> =
            mRepository.singInViaFacebook(AuthMapper.mapConvertTokenRequest(token, "google-oauth2", mRepository.getAuthCredentials()))
                    .map { mapSingInResponse(it) }
                    .applySchedulers()

    override fun singInViaVk(token: String): Single<Monade> =
            mRepository.singInViaFacebook(AuthMapper.mapConvertTokenRequest(token, "vk-oauth2", mRepository.getAuthCredentials()))
                    .map { mapSingInResponse(it) }
                    .applySchedulers()

    override fun singInViaTwitter(token: String): Single<Monade> =
            mRepository.singInViaFacebook(AuthMapper.mapConvertTokenRequest(token, "twitter", mRepository.getAuthCredentials()))
                    .map { mapSingInResponse(it) }
                    .applySchedulers()

    override fun singInViaGooglePlus(): Observable<Monade> =
            mRepository.singInViaGooglePlus()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())

    override fun singIn(login: String, password: String): Observable<Monade> =
            mRepository.singIn(AuthMapper.mapSingInDataToRequest(login, password, mRepository.getAuthCredentials()))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .map { mapSingInResponse(it) }

    override fun singUp(singUpData: SingUpData): Observable<Monade> =
            mRepository.singUp(AuthMapper.mapSingUpDataToRequest(singUpData))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .map { BaseMapper.mapBaseResponse(it) }

    override fun restorePassword(email: String, phone: String): Single<Monade> =
            mRepository.restorePassword(RestorePasswordRequest(email, phone.replace("(\\(|\\)| )", "")))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .map { AuthMapper.mapRestorePasswordDataResponse(it) }


    private fun mapSingInResponse(response: SingInResponse): Monade {
        val monade = AuthMapper.mapSingInResponse(response)
        if (!monade.isError)
            mRepository.saveToken(AuthMapper.mapToken(response))
        return monade
    }
}