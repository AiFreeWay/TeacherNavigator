package com.teachernavigator.teachernavigator.data.repository

import android.content.Context
import android.text.TextUtils
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.data.cache.CacheController
import com.teachernavigator.teachernavigator.data.models.PostNetwork
import com.teachernavigator.teachernavigator.data.network.NetworkController
import com.teachernavigator.teachernavigator.data.network.requests.RestorePasswordRequest
import com.teachernavigator.teachernavigator.data.network.fieldmapskeys.SavePostFieldKeys
import com.teachernavigator.teachernavigator.data.network.requests.SingInRequest
import com.teachernavigator.teachernavigator.data.network.requests.SingUpRequest
import com.teachernavigator.teachernavigator.data.network.responses.BaseResponse
import com.teachernavigator.teachernavigator.data.network.responses.GetMyCommentsResponse
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
        Logger.logDebug("created REPOSITORY MainRepository")
    }

    override fun getAccessToken(): String {
        val token = (CacheController.getData(CacheController.TOKEN_KEY, Token.EMPTY_TOKEN) as Token)
        return token.tokenType+" "+token.accessToken
    }

    // ------------------------------- Auth methods --------------------------------

    override fun getTokenAsynch(): Observable<Token> =
            Observable.just(CacheController.getData(CacheController.TOKEN_KEY, Token.EMPTY_TOKEN))

    override fun isAuth(): Boolean = !TextUtils.isEmpty(getAccessToken())

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

    override fun getInterviewsPosts(): Observable<Array<PostNetwork>> = mNetwokController.getInterviewsPosts()

    override fun getNewsPosts(): Observable<Array<PostNetwork>> = mNetwokController.getNewsPosts()

    override fun getPosts(): Observable<Array<PostNetwork>> = mNetwokController.getPosts()

    override fun getPostById(postId: Int): Observable<PostNetwork> = mNetwokController.getPost(postId)

    override fun getMyComments(): Observable<GetMyCommentsResponse>
            = mNetwokController.getMyComments(getAccessToken())

    override fun savePost(filedMap: Map<String, String>): Observable<BaseResponse> =
            mNetwokController.savePost(getAccessToken(), filedMap)
}