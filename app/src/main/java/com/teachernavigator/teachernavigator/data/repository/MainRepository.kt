package com.teachernavigator.teachernavigator.data.repository

import android.content.Context
import android.text.TextUtils
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.data.cache.CacheController
import com.teachernavigator.teachernavigator.data.models.CommentNetwork
import com.teachernavigator.teachernavigator.data.models.PostNetwork
import com.teachernavigator.teachernavigator.data.network.NetworkController
import com.teachernavigator.teachernavigator.data.network.requests.*
import com.teachernavigator.teachernavigator.data.network.responses.BaseResponse
import com.teachernavigator.teachernavigator.data.network.responses.GetMyCommentsResponse
import com.teachernavigator.teachernavigator.data.network.responses.PostsResponse
import com.teachernavigator.teachernavigator.data.network.responses.SingInResponse
import com.teachernavigator.teachernavigator.data.repository.abstractions.IMainRepository
import com.teachernavigator.teachernavigator.domain.models.*
import io.reactivex.Observable
import io.reactivex.Single
import java.io.File
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
        token.tokenType + " " + token.accessToken
        return token.tokenType + " " + token.accessToken
    }

    // ------------------------------- Auth methods --------------------------------

    override fun getTokenAsynch(): Observable<Token> =
            Observable.just(CacheController.getData(CacheController.TOKEN_KEY, Token.EMPTY_TOKEN))

    override fun isAuth(): Boolean {
        val token = (CacheController.getData(CacheController.TOKEN_KEY, Token.EMPTY_TOKEN) as Token)
        return !TextUtils.isEmpty(token.accessToken)
    }

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

    override fun getInterviewsPosts(): Observable<PostsResponse> = mNetwokController.getInterviewsPosts(getAccessToken())

    override fun getNewsPosts(): Observable<PostsResponse> = mNetwokController.getNewsPosts(getAccessToken())

    override fun getPosts(): Observable<PostsResponse> {
        if (isAuth())
            return mNetwokController.getPosts(getAccessToken())
        return mNetwokController.getPosts()
    }

    override fun getPostById(postId: Int): Observable<PostNetwork> = mNetwokController.getPost(getAccessToken(), postId)

    override fun getMyComments(): Observable<GetMyCommentsResponse>
            = mNetwokController.getMyComments(getAccessToken())

    override fun getSavedPosts(): Observable<PostsResponse>
            = mNetwokController.getSavedPosts(getAccessToken())

    override fun savePost(request: SavePostRequest): Observable<BaseResponse> =
            mNetwokController.savePost(getAccessToken(), request)

    override fun comment(request: CommentRequest): Observable<CommentNetwork> =
            mNetwokController.comment(getAccessToken(), request)

    override fun subscribe(request: SubscribeRequest): Observable<BaseResponse> =
            mNetwokController.subscribe(getAccessToken(), request)

    override fun vote(request: VoteRequest): Observable<BaseResponse> =
            mNetwokController.vote(getAccessToken(), request)

    override fun getMyPublications(): Observable<Array<PostNetwork>> =
            mNetwokController.getMyPublications(getAccessToken())

    override fun getUserPost(userId: Int): Observable<Array<PostNetwork>> =
            mNetwokController.getUserPost(getAccessToken(), userId)

    // ------------------------------- Settings methods --------------------------------

    override fun getSettings(): Observable<Settings> {
        val settings = CacheController.getData(CacheController.SETTINGS_KEY, Settings())
        return Observable.just(settings)
    }

    override fun putSettings(settings: Settings) {
        CacheController.putData(CacheController.SETTINGS_KEY, settings)
    }

    // ------------------------------- Profile methods --------------------------------

    override fun getProfile(): Observable<Profile> = mNetwokController.getProfile(getAccessToken())

    override fun getProfile(userId: Int): Observable<Profile> = mNetwokController.getProfile(getAccessToken(), userId)

    override fun exit() {
        CacheController.removeData(CacheController.TOKEN_KEY)
    }

    override fun uploadPhoto(file: File): Observable<File> {
        return Observable.just(file)
    }

    // ------------------------------- Job methods --------------------------------
    override fun createVacancy(vacancyRequest: VacancyRequest): Single<Vacancy> =
            mNetwokController.createVacancy(getAccessToken(), vacancyRequest)

    override fun getTypesOfEmployment(): List<TypeOfEmployment> =
            mContext.resources.getStringArray(R.array.types_of_employment)
                    .mapIndexed { index, resId -> TypeOfEmployment(index, resId) }

    override fun loadMyVacancies(): Single<List<Vacancy>> =
            mNetwokController.loadMyVacancies(getAccessToken())

    override fun loadMyResume(): Single<List<Resume>> =
            mNetwokController.loadMyResume(getAccessToken())

    override fun createResume(resumeRequest: ResumeRequest): Single<Resume> =
            mNetwokController.createResume(getAccessToken(), resumeRequest)

    override fun loadResumeList(): Single<List<Resume>> =
            mNetwokController.loadResumeList(getAccessToken())

    override fun loadVacancies(): Single<List<Vacancy>> =
            mNetwokController.loadVacancies(getAccessToken())

    override fun loadVacancy(vacancyId: Int): Single<Vacancy> =
            mNetwokController.loadVacancy(getAccessToken(), vacancyId)

    override fun removeVacancy(vacancyId: Int): Single<Unit> =
            mNetwokController.removeVacancy(getAccessToken(), vacancyId)

    override fun prolongVacancy(vacancyId: Int): Single<Unit> =
            mNetwokController.prolongVacancy(getAccessToken(), vacancyId)

    override fun prolongResume(resumeId: Int): Single<Unit> =
            mNetwokController.prolongResume(getAccessToken(), resumeId)

    override fun removeResume(resumeId: Int): Single<Unit> =
            mNetwokController.removeResume(getAccessToken(), resumeId)

    override fun respondVacancy(vacancyId: Int): Single<Unit> =
            mNetwokController.respondVacancy(getAccessToken(), vacancyId)

    // ------------------------------- Info methods --------------------------------
    override fun loadAbout(): Single<List<About>> =
            mNetwokController.loadAbout(getAccessToken())


}