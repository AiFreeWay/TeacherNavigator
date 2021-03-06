package com.teachernavigator.teachernavigator.data.network

import android.support.annotation.VisibleForTesting
import com.google.gson.Gson
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.data.models.CommentNetwork
import com.teachernavigator.teachernavigator.data.models.FileInfo
import com.teachernavigator.teachernavigator.data.models.PostCommentNetwork
import com.teachernavigator.teachernavigator.data.models.PostNetwork
import com.teachernavigator.teachernavigator.data.network.requests.*
import com.teachernavigator.teachernavigator.data.network.responses.BaseListResponse
import com.teachernavigator.teachernavigator.data.network.responses.BaseResponse
import com.teachernavigator.teachernavigator.data.network.responses.PostsResponse
import com.teachernavigator.teachernavigator.data.network.responses.SingInResponse
import com.teachernavigator.teachernavigator.data.utils.toRequestBody
import com.teachernavigator.teachernavigator.domain.models.*
import com.teachernavigator.teachernavigator.presentation.models.Specialist
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by root on 11.08.17
 */
@Singleton
class NetworkController
@Inject
constructor(gson: Gson) {

    companion object {
        const val DOMAIN = "pronm.pr-solution.ru"
        const val HTTP = "http://"
        const val SERVER = "$HTTP$DOMAIN"

        private const val API_URL = "$SERVER/"

        private const val ANDROID = "android"

        private const val MAX_PAGE_COUNT = 1000
    }

    private val mApiController: ApiController

    private var client: OkHttpClient?

    init {

        val httpClient = OkHttpClient.Builder()
                .addInterceptor(createHeadersInterceptor())

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
        }

        val client = httpClient.build()
        this.client = client
        val retrofit = Retrofit.Builder()
                .baseUrl(API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
        mApiController = retrofit.create(ApiController::class.java)
    }

    @VisibleForTesting
    private fun createHeadersInterceptor() = Interceptor { chain ->
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("Accept-Language", Locale.getDefault().language)
        requestBuilder.addHeader("Accept", "application/json")
        chain.proceed(requestBuilder.build())
    }

    // ------------------------------- Auth methods --------------------------------

    fun singUp(request: SingUpRequest): Single
    <BaseResponse> = mApiController.singUp(request)

    fun singIn(request: SingInRequest): Single<SingInResponse> = mApiController.singIn(request)


    fun convertToken(request: ConvertTokenRequest): Single<SingInResponse> =
            mApiController.convertToken(request)

    fun googleAuth(code: String): Single<SingInResponse> =
            mApiController.googleAuth(code)

    fun restorePassword(request: RestorePasswordRequest): Single<BaseResponse> =
            mApiController.restorePassword(request)

    // ------------------------------- Posts methods --------------------------------

    fun getPolls(token: String?): Single<PostsResponse> = mApiController.getPolls(token)

    fun getNewsPosts(token: String): Single<PostsResponse> = mApiController.getNews(token)

    fun getPosts(token: String?): Single<PostsResponse> = mApiController.getPosts(token)

    fun getBestPosts(token: String): Single<PostsResponse> = mApiController.getBestPosts(token)

    fun getPost(token: String, postId: Int): Single<PostNetwork> = mApiController.getPost(token, postId)

    fun getNews(token: String, newsId: Int): Single<PostNetwork> = mApiController.getNews(token, newsId)

    fun getPoll(token: String, pollId: Int): Single<PostNetwork> = mApiController.getPoll(token, pollId)

    fun savePost(token: String, postId: Int): Single<BaseResponse> = mApiController.savePost(token, postId)

    fun savePoll(token: String, postId: Int): Single<BaseResponse> = mApiController.savePoll(token, postId)

    fun saveNews(token: String, postId: Int): Single<BaseResponse> = mApiController.saveNews(token, postId)

    fun getMyComments(token: String): Single<List<PostCommentNetwork>> =
            mApiController.myComments(token)
                    .map { it.results }

    fun getSavedPosts(token: String): Single<PostsResponse> = mApiController.getSavedPosts(token)

    fun getSavedImportantInfos(token: String): Single<PostsResponse> = mApiController.getSavedImportantInfos(token)
    fun getSavedPolls(token: String): Single<PostsResponse> = mApiController.getSavedPolls(token)
    fun getSavedNews(token: String): Single<PostsResponse> = mApiController.getSavedNews(token)
    fun getQuestionAnswerPosts(token: String): Single<PostsResponse> = mApiController.getQuestionAnswerPosts(token)
    fun getAdvicesPosts(token: String): Single<PostsResponse> = mApiController.getAdvicesPosts(token)

    fun getMyPosts(token: String): Single<BaseListResponse<PostNetwork>> = mApiController.getMyPosts(token)

    fun comment(token: String, request: CommentRequest): Single<CommentNetwork> = mApiController.comment(token, request)

    fun subscribe(token: String, request: SubscribeRequest): Single<BaseResponse> = mApiController.subscribe(token, request)

    fun getUserPost(token: String, userId: Int): Single<BaseListResponse<PostNetwork>> = mApiController.getUserPost(token, userId)


    fun vote(token: String, request: VoteRequest): Single<BaseResponse> {
        val map = HashMap<String, String>()
        map.put("model", request.model)
        map.put("id", request.id.toString())
        map.put("vote", request.like.toString())
        return mApiController.vote(token, map)
    }

    // ------------------------------- Profile methods --------------------------------

    fun getProfile(token: String): Single<Profile> = mApiController.getProfile(token)

    fun getProfile(token: String, userId: Int): Single<Profile> = mApiController.getProfile(token, userId)

    fun createVacancy(token: String, vacancyRequest: VacancyRequest): Single<Vacancy> =
            mApiController.createVacancy(token, vacancyRequest)

    fun loadMyVacancies(accessToken: String): Single<List<Vacancy>> =
            mApiController.myVacancies(accessToken)
                    .map { it.results }

    fun loadMyResume(accessToken: String): Single<List<Resume>> =
            mApiController.myResume(accessToken)
                    .map { it.results }

    fun createResume(accessToken: String, resumeRequest: ResumeRequest, fileInfo: FileInfo?): Single<Resume> =
            if (fileInfo != null) {
                val params = HashMap<String, RequestBody>().apply {
                    put("career_objective", resumeRequest.careerObjective.toRequestBody())
                    put("district_council", resumeRequest.districtCouncil.toString().toRequestBody())
                    put("amount_of_wages", resumeRequest.salary.toRequestBody())
                    put("education", resumeRequest.education.toRequestBody())
                    put("experience", resumeRequest.experience.toRequestBody())
                    put("file\"; filename=\"${fileInfo.fileName}", fileInfo.toRequestBody())
                }
                mApiController.createResumeAndUpload(accessToken, params)
            } else {
                mApiController.createResume(accessToken, resumeRequest)
            }

    fun uploadAvatar(accessToken: String, fileInfo: FileInfo): Single<BaseResponse> =
            mApiController.uploadAvatar(accessToken, mapOf("avatar\"; filename=\"${fileInfo.fileName}" to fileInfo.toRequestBody()))

    fun loadResumeList(accessToken: String): Single<List<Resume>> =
            mApiController.resumeList(accessToken)
                    .map { it.results }

    fun loadVacancies(accessToken: String): Single<List<Vacancy>> =
            mApiController.vacancies(accessToken)
                    .map { it.results }

    fun loadVacancy(accessToken: String, vacancyId: Int): Single<Vacancy> =
            mApiController.vacancy(accessToken, vacancyId)

    fun removeVacancy(accessToken: String, vacancyId: Int): Single<Unit> =
            mApiController.removeVacancy(accessToken, vacancyId)
                    .toSingle(Unit)// TODO Temporary, I do not have any time

    fun prolongVacancy(accessToken: String, vacancyId: Int): Single<Unit> =
            mApiController.updateVacancy(accessToken, vacancyId)
                    .toSingle(Unit)// TODO Temporary, I don't have at all

    fun prolongResume(accessToken: String, resumeId: Int): Single<Unit> =
            mApiController.updateResume(accessToken, resumeId)
                    .toSingle(Unit)// TODO Temporary, I don't have at all

    fun removeResume(accessToken: String, resumeId: Int): Single<Unit> =
            mApiController.removeResume(accessToken, resumeId)
                    .toSingle(Unit)// TODO Temporary, I do not have any time

    fun respondVacancy(accessToken: String, vacancyId: Int): Single<Unit> =
            mApiController.respondVacancy(accessToken, RespondVacancyRequest(vacancyId))
                    .toSingle(Unit)// TODO Temporary, I do not have any time

    fun loadAbout(accessToken: String): Single<List<About>> =
            mApiController.loadAbout(accessToken)
                    .map { it.results }

    fun askSpecialist(accessToken: String, specialist: Specialist, question: String): Single<Unit> =
            mApiController.askSpecialist(accessToken, specialist.ordinal, question)
                    .toSingle(Unit)

    fun getInfoPosts(accessToken: String, themeId: Int): Single<PostsResponse> =
            mApiController.getInfoPosts(accessToken, themeId)

    fun getInfoPost(accessToken: String, postId: Int): Single<PostNetwork> =
            mApiController.getInfoPost(accessToken, postId)

    fun saveInfoPost(accessToken: String, postId: Int): Single<Unit> =
            mApiController.saveInfoPost(accessToken, postId)

    fun complaintInfo(accessToken: String, postId: Int): Single<BaseResponse> =
            mApiController.complaintInfo(accessToken, postId)

    fun complaintPost(accessToken: String, postId: Int): Single<BaseResponse> =
            mApiController.complaintPost(accessToken, postId)

    fun complaintNews(accessToken: String, postId: Int): Single<BaseResponse> =
            mApiController.complaintNews(accessToken, postId)

    fun complaintPoll(accessToken: String, postId: Int): Single<BaseResponse> =
            mApiController.complaintPoll(accessToken, postId)

    fun passPoll(accessToken: String, postId: Int, choiceId: Int): Single<BaseResponse> =
            mApiController.passPoll(accessToken, postId, choiceId)

    fun updateFCMToken(accessToken: String, deviceName: String, fcmToken: String, deviceId: String, active: Boolean): Single<BaseResponse> =
            mApiController.registerDevices(accessToken, deviceName, fcmToken, deviceId, active, ANDROID)


    fun loadMessages(accessToken: String): Single<List<Message>> =
            mApiController.chatHistory(accessToken)
                    .map { it.results }

    fun loadMessage(accessToken: String, messageId: Int): Single<Message> =
            mApiController.chatHistory(accessToken, messageId)

    fun getTags(accessToken: String): Single<List<Tag>> =
            loadTags(accessToken)
                    .map { it.results }

    fun getTrends(accessToken: String): Single<List<Tag>> =
            loadTrends(accessToken)
                    .map { it.results }

    private fun loadTags(accessToken: String) = mApiController.tags(accessToken)
    private fun loadTrends(accessToken: String) = mApiController.tagsTrends(accessToken)


    fun sendPost(accessToken: String, title: String, text: String, tags: List<String>, fileInfo: FileInfo?): Single<PostNetwork> {

        val params = HashMap<String, RequestBody>().apply {
            put("title", title.toRequestBody())
            put("text", text.toRequestBody())
            put("tags", tags.toRequestBody())

            if (fileInfo != null) {
                put("file\"; filename=\"${fileInfo.fileName}", fileInfo.toRequestBody())
            }
        }

        return mApiController.sendPost(accessToken, params)
    }

    fun editProfile(accessToken: String, singUpData: EditProfileData): Single<EditProfileData> =
            mApiController.editProfile(accessToken, singUpData)

    fun getProfileSettings(accessToken: String): Single<EditProfileData> =
            mApiController.getProfileSettings(accessToken)

}
