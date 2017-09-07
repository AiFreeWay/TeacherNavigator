package com.teachernavigator.teachernavigator.data.repository

import android.content.Context
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.data.cache.CacheController
import com.teachernavigator.teachernavigator.data.models.PostNetwork
import com.teachernavigator.teachernavigator.data.network.NetworkController
import com.teachernavigator.teachernavigator.data.network.requests.RestorePasswordRequest
import com.teachernavigator.teachernavigator.data.network.requests.SingInRequest
import com.teachernavigator.teachernavigator.data.network.requests.SingUpRequest
import com.teachernavigator.teachernavigator.data.network.responses.BaseResponse
import com.teachernavigator.teachernavigator.data.network.responses.SingInResponse
import com.teachernavigator.teachernavigator.data.repository.abstractions.IMainRepository
import com.teachernavigator.teachernavigator.domain.models.AuthCredentials
import com.teachernavigator.teachernavigator.domain.models.Monade
import com.teachernavigator.teachernavigator.domain.models.Token
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by root on 22.08.17.
 */
class MainRepository @Inject constructor(private val mNetwokController: NetworkController,
                                         private val mContext: Context) : IMainRepository {

    init {
        if (BuildConfig.DEBUG) Logger.logDebug("created REPOSITORY MainRepository")
    }

    // ------------------------------- Auth methods --------------------------------

    override fun getToken(): Observable<Token> =
            Observable.just(CacheController.getData(CacheController.TOKEN_KEY, Token.EMPTY_TOKEN))

    override fun saveToken(token: Token) {
        CacheController.putData(CacheController.TOKEN_KEY, token)
    }

    override fun singInViaVkontakte(): Observable<Monade> = Observable.just(Monade(false))

    override fun singInViaFacebook(): Observable<Monade> = Observable.just(Monade(false))

    override fun singInViaTwitter(): Observable<Monade> = Observable.just(Monade(false))

    override fun singInViaGooglePlus(): Observable<Monade> = Observable.just(Monade(false))

    override fun singIn(request: SingInRequest): Observable<SingInResponse> =
            mNetwokController.singIn(request)

    override fun singUp(request: SingUpRequest): Observable<BaseResponse> =
            mNetwokController.singUp(request)

    override fun getAuthCredentials(): AuthCredentials {
        val clientId = mContext.getString(R.string.client_id)
        val clientSecret = mContext.getString(R.string.client_secret)
        val grantType = mContext.getString(R.string.grant_type)
        return AuthCredentials(clientId, clientSecret, grantType)
    }

    override fun restorePassword(request: RestorePasswordRequest): Observable<BaseResponse> =
            mNetwokController.restorePassword(request)

    // ------------------------------- Posts methods --------------------------------

    override fun getBestPosts(): Observable<Array<PostNetwork>> = mNetwokController.getBestPosts()

    override fun getInterviewsPosts(): Observable<Array<PostNetwork>> = mNetwokController.getInterviewsPosts()

    override fun getLatestPosts(): Observable<Array<PostNetwork>> = mNetwokController.getLatestPosts()

    override fun getNewsPosts(): Observable<Array<PostNetwork>> = mNetwokController.getNewsPosts()

    override fun getPostById(postId: Int): Observable<PostNetwork> = mNetwokController.getPost(postId)
}