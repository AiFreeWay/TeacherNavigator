package com.teachernavigator.teachernavigator.domain.interactors

import com.teachernavigator.teachernavigator.data.models.SocialNetwork
import com.teachernavigator.teachernavigator.data.network.requests.RestorePasswordRequest
import com.teachernavigator.teachernavigator.data.network.responses.SingInResponse
import com.teachernavigator.teachernavigator.data.repository.abstractions.IAuthRepository
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IAuthInteractor
import com.teachernavigator.teachernavigator.domain.mappers.AuthMapper
import com.teachernavigator.teachernavigator.domain.mappers.BaseMapper
import com.teachernavigator.teachernavigator.domain.models.Monade
import com.teachernavigator.teachernavigator.domain.models.SingUpData
import com.teachernavigator.teachernavigator.presentation.utils.applySchedulers
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by root on 17.08.17
 */
class AuthInteractor @Inject constructor(private val mRepository: IAuthRepository) : IAuthInteractor {

    override fun isAuthAsync(): Single<Boolean> =
            mRepository.getTokenAsync()
                    .applySchedulers()
                    .map { it.isExists() }

    override fun singInViaFacebook(token: String): Single<Monade> =
            mRepository.createSocialAccount(SocialNetwork.Facebook, token)
                    .flatMap { mRepository.singInViaSocials(AuthMapper.mapConvertTokenRequest(token, SocialNetwork.Facebook, mRepository.getAuthCredentials())) }
                    .map { mapSingInResponse(it) }
                    .applySchedulers()

    override fun singInViaGoogle(code: String): Single<Monade> =
            mRepository.singInViaGoogle(code)
                    .map { mapSingInResponse(it) }
                    .sendFCM()
                    .applySchedulers()

    override fun singInViaVk(token: String): Single<Monade> =
            mRepository.createSocialAccount(SocialNetwork.Vk, token)
                    .flatMap { mRepository.singInViaSocials(AuthMapper.mapConvertTokenRequest(token, SocialNetwork.Vk, mRepository.getAuthCredentials())) }
                    .map { mapSingInResponse(it) }
                    .sendFCM()
                    .applySchedulers()

    override fun singInViaTwitter(oauthToken: String, oauthTokenSecret: String): Single<Monade> {
        val token = "oauth_token=$oauthToken&oauth_token_secret=$oauthTokenSecret"
       return mRepository.createSocialAccount(SocialNetwork.Twitter, token)
                .flatMap { mRepository.singInViaSocials(AuthMapper.mapConvertTokenRequest(token, SocialNetwork.Twitter, mRepository.getAuthCredentials())) }
                .map { mapSingInResponse(it) }
                .sendFCM()
                .applySchedulers()
    }

    override fun singIn(login: String, password: String): Single<Monade> =
            mRepository.singIn(AuthMapper.mapSingInDataToRequest(login, password, mRepository.getAuthCredentials()))
                    .map { mapSingInResponse(it) }
                    .sendFCM()
                    .applySchedulers()

    override fun singUp(singUpData: SingUpData): Single<Monade> =
            mRepository.singUp(AuthMapper.mapSingUpDataToRequest(singUpData))
                    .map { BaseMapper.mapBaseResponse(it) }
                    .sendFCM()
                    .applySchedulers()

    override fun restorePassword(email: String, phone: String): Single<Monade> =
            mRepository.restorePassword(RestorePasswordRequest(email, phone.replace("(\\(|\\)| )", "")))
                    .applySchedulers()
                    .map { AuthMapper.mapRestorePasswordDataResponse(it) }

    private fun mapSingInResponse(response: SingInResponse): Monade {
        val monade = AuthMapper.mapSingInResponse(response)
        if (!monade.isError) {
            mRepository.saveToken(AuthMapper.mapToken(response))
        }
        return monade
    }

    override fun updateFCMToken() = mRepository.updateFCMToken()


    private fun Single<Monade>.sendFCM() = map { it.apply { if (!isError) updateFCMToken() } }

}