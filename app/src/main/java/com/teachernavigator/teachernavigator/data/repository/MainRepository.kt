package com.teachernavigator.teachernavigator.data.repository

import android.content.Context
import android.os.Build
import android.support.v4.util.SparseArrayCompat
import android.text.TextUtils
import com.google.firebase.iid.FirebaseInstanceId
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.data.cache.CacheController
import com.teachernavigator.teachernavigator.data.cache.CacheController.Companion.USER_KEY
import com.teachernavigator.teachernavigator.data.models.*
import com.teachernavigator.teachernavigator.data.network.NetworkController
import com.teachernavigator.teachernavigator.data.network.requests.*
import com.teachernavigator.teachernavigator.data.network.responses.BaseListResponse
import com.teachernavigator.teachernavigator.data.network.responses.BaseResponse
import com.teachernavigator.teachernavigator.data.network.responses.PostsResponse
import com.teachernavigator.teachernavigator.data.network.responses.SingInResponse
import com.teachernavigator.teachernavigator.data.repository.abstractions.IMainRepository
import com.teachernavigator.teachernavigator.data.utils.isPollPassed
import com.teachernavigator.teachernavigator.data.utils.setPollPassed
import com.teachernavigator.teachernavigator.domain.interactors.Filter
import com.teachernavigator.teachernavigator.domain.models.*
import com.teachernavigator.teachernavigator.presentation.models.Info
import com.teachernavigator.teachernavigator.presentation.models.Specialist
import com.teachernavigator.teachernavigator.presentation.utils.applySchedulers
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by root on 22.08.17
 */
class MainRepository @Inject constructor(private val mNetwokController: NetworkController,
                                         private val mContext: Context) : IMainRepository {

    override fun putFilter(ordinal: Int, filter: Filter) {
        mFilters.put(ordinal, filter)
    }

    override fun getFilter(pos: Int): Filter? =
            mFilters.get(pos)

    private val mFilters: SparseArrayCompat<Filter> = SparseArrayCompat()

    override fun currentUserId(): Int =
            (CacheController.getData(USER_KEY, null) as? Profile)?.id ?: -1

    // TODO Make it nullable
    override fun getAccessToken(): String {
        val token = (CacheController.getData(CacheController.TOKEN_KEY, Token.EMPTY_TOKEN) as Token)

        if (token.accessToken.isBlank()) {
            return ""
        }

        token.tokenType + " " + token.accessToken
        return token.tokenType + " " + token.accessToken
    }

    override fun accessToken(): String {
        val token = (CacheController.getData(CacheController.TOKEN_KEY, Token.EMPTY_TOKEN) as Token)
        return token.accessToken
    }


    // ------------------------------- Auth methods --------------------------------

    override fun getTokenAsync(): Single<Token> =
            Single.just(CacheController.getData(CacheController.TOKEN_KEY, Token.EMPTY_TOKEN))

    override fun isAuth(): Boolean {
        val token = (CacheController.getData(CacheController.TOKEN_KEY, Token.EMPTY_TOKEN) as Token)
        return !TextUtils.isEmpty(token.accessToken)
    }

    override fun saveToken(token: Token) {
        CacheController.putData(CacheController.TOKEN_KEY, token)
    }

    override fun singInViaSocials(request: ConvertTokenRequest): Single<SingInResponse> =
            mNetwokController.convertToken(request)

    override fun singInViaGoogle(code: String): Single<SingInResponse> =
            mNetwokController.googleAuth(code)

    override fun singInViaTwitter(): Observable<Monade> = Observable.just(Monade(false))

    override fun singIn(request: SingInRequest): Single<SingInResponse> =
            mNetwokController.singIn(request)

    override fun singUp(request: SingUpRequest): Single<BaseResponse> =
            mNetwokController.singUp(request)


    override fun getAuthCredentials(): AuthCredentials {
        val clientId = mContext.getString(R.string.client_id)
        val clientSecret = mContext.getString(R.string.client_secret)
        return AuthCredentials(clientId, clientSecret)
    }

    override fun restorePassword(request: RestorePasswordRequest): Single<BaseResponse> =
            mNetwokController.restorePassword(request)

    override fun updateFCMToken() {
        mNetwokController.updateFCMToken(getAccessToken(), deviceName, fcmToken, deviceId, CacheController.isPushEnabled())
                .applySchedulers()
                .subscribe { _, throwable ->
                    throwable?.printStackTrace()
                }
    }

    override fun updateFCM() {
        updateFCMToken()
    }

    // ------------------------------- Posts methods --------------------------------

    override fun getPolls(): Single<PostsResponse> =
            mNetwokController.getPolls(getAccessToken())

    override fun getNewsPosts(): Single<PostsResponse> =
            mNetwokController.getNewsPosts(getAccessToken())

    override fun getPosts(): Single<PostsResponse> =
            mNetwokController.getPosts(getAccessToken())

    override fun getBestPosts(): Single<PostsResponse> =
            mNetwokController.getBestPosts(getAccessToken())

    override fun getMyComments(): Single<List<PostCommentNetwork>>
            = mNetwokController.getMyComments(getAccessToken())

    override fun getSavedPosts(): Single<PostsResponse>
            = mNetwokController.getSavedPosts(getAccessToken())

    override fun getSavedImportantInfos(): Single<PostsResponse>
            = mNetwokController.getSavedImportantInfos(getAccessToken())

    override fun getSavedPolls(): Single<PostsResponse>
            = mNetwokController.getSavedPolls(getAccessToken())

    override fun getSavedNews(): Single<PostsResponse>
            = mNetwokController.getSavedNews(getAccessToken())

    override fun getQuestionAnswerPosts(): Single<PostsResponse>
            = mNetwokController.getQuestionAnswerPosts(getAccessToken())

    override fun getAdvicesPosts(): Single<PostsResponse>
            = mNetwokController.getAdvicesPosts(getAccessToken())

    override fun getMyPosts(): Single<BaseListResponse<PostNetwork>>
            = mNetwokController.getMyPosts(getAccessToken())

    override fun comment(request: CommentRequest): Single<CommentNetwork> =
            mNetwokController.comment(getAccessToken(), request)

    override fun subscribe(authorId: Int): Single<BaseResponse> =
            mNetwokController.subscribe(getAccessToken(), SubscribeRequest(authorId))
                    .applySchedulers()

    override fun vote(postId: Int, isLike: Boolean, type: PostType): Single<BaseResponse> =
            mNetwokController.vote(getAccessToken(), VoteRequest(postId, isLike, if (type == PostType.poll) "question" else type.name))
                    .applySchedulers()

    override fun complaint(postId: Int, type: PostType): Single<BaseResponse> = when (type) {

        PostType.importantinfo -> mNetwokController.complaintInfo(getAccessToken(), postId)
        PostType.post -> mNetwokController.complaintPost(getAccessToken(), postId)
        PostType.poll -> mNetwokController.complaintPoll(getAccessToken(), postId)
        PostType.news -> mNetwokController.complaintNews(getAccessToken(), postId)

    }.applySchedulers()

    override fun getUserPost(userId: Int): Single<BaseListResponse<PostNetwork>> =
            mNetwokController.getUserPost(getAccessToken(), userId)

    override fun getInfoPosts(currentTheme: Info): Single<PostsResponse> =
            mNetwokController.getInfoPosts(getAccessToken(), currentTheme.pathId)

    override fun getPost(postId: Int, postType: PostType): Single<PostNetwork> = when (postType) {

        PostType.importantinfo -> mNetwokController.getInfoPost(getAccessToken(), postId)
        PostType.post -> mNetwokController.getPost(getAccessToken(), postId)
        PostType.news -> mNetwokController.getNews(getAccessToken(), postId)
        PostType.poll -> mNetwokController.getPoll(getAccessToken(), postId)
    }

    override fun save(postId: Int, type: PostType): Single<Unit> = when (type) {
        PostType.importantinfo -> mNetwokController.saveInfoPost(getAccessToken(), postId)
        PostType.poll -> mNetwokController.savePoll(getAccessToken(), postId)
        PostType.news -> mNetwokController.saveNews(getAccessToken(), postId)
        PostType.post -> mNetwokController.savePost(getAccessToken(), postId)
    }
            .map { Unit }

    override fun passPoll(postId: Int, choiceId: Int) =
            mNetwokController.passPoll(getAccessToken(), postId, choiceId)
                    .doOnSuccess { if (!it.is_error) mContext.setPollPassed(postId) }!!

    override fun getTags(): Single<List<Tag>> =
            mNetwokController.getTags(getAccessToken())

    override fun getTrends(): Single<List<Tag>> =
            mNetwokController.getTrends(getAccessToken())

    override fun sendPost(title: String, text: String, tags: List<String>, fileInfo: FileInfo?): Single<PostNetwork> =
            mNetwokController.sendPost(getAccessToken(), title, text, tags, fileInfo)

    // ------------------------------- Settings methods --------------------------------

    override fun getSettings(): Observable<Settings> {
        val settings = CacheController.getData(CacheController.SETTINGS_KEY, Settings())
        return Observable.just(settings)
    }

    override fun putSettings(settings: Settings) {
        CacheController.putData(CacheController.SETTINGS_KEY, settings)
    }

    // ------------------------------- Profile methods --------------------------------

    override fun getProfile(): Single<Profile> =
            mNetwokController.getProfile(getAccessToken())
                    .doOnSuccess { CacheController.putData(USER_KEY, it) }

    override fun getProfile(userId: Int): Single<Profile> = mNetwokController.getProfile(getAccessToken(), userId)

    override fun editProfile(singUpData: EditProfileData): Single<EditProfileData>
            = mNetwokController.editProfile(getAccessToken(), singUpData)

    override fun getProfileSettings(): Single<EditProfileData>
            = mNetwokController.getProfileSettings(getAccessToken())

    override fun exit() {
        CacheController.removeData(CacheController.TOKEN_KEY)
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

    override fun createResume(resumeRequest: ResumeRequest, fileInfo: FileInfo?): Single<Resume> =
            mNetwokController.createResume(getAccessToken(), resumeRequest, fileInfo)

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


    override fun uploadAvatar(fileInfo: FileInfo): Single<Unit> =
            mNetwokController.uploadAvatar(getAccessToken(), fileInfo)
                    .map { Unit }

    override fun createSocialAccount(socialNetwork: SocialNetwork, token: String): Single<Profile> =
            mNetwokController.getProfile("Bearer ${socialNetwork.tokenName} $token")

    // ------------------------------- Info methods --------------------------------
    override fun loadAbout(): Single<List<About>> =
            mNetwokController.loadAbout(getAccessToken())

    override fun askSpecialist(specialist: Specialist, question: String): Single<Unit> =
            mNetwokController.askSpecialist(getAccessToken(), specialist, question)


    /* Storage Methods */
    override fun isPollPassed(id: Int) = mContext.isPollPassed(id)

    override fun sedPollPassed(id: Int) = mContext.setPollPassed(id)


    private val deviceName: String
        get() = "${Build.DEVICE}_${Build.MANUFACTURER}_${Build.MODEL}"

    private val fcmToken: String
        get() = FirebaseInstanceId.getInstance().token ?: ""

    private val deviceId: String
        get() = CacheController.getDeviceId()

    // ------------------------------- Chat methods --------------------------------

    override fun loadMessages(): Single<List<Message>>
            = mNetwokController.loadMessages(getAccessToken())

    override fun loadMessage(messageId: Int): Single<Message>
            = mNetwokController.loadMessage(getAccessToken(), messageId)


}