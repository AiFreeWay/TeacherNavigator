package com.teachernavigator.teachernavigator.data.network

import com.example.root.androidtest.application.utils.Logger
import com.google.gson.GsonBuilder
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.data.models.CommentNetwork
import com.teachernavigator.teachernavigator.data.models.PostNetwork
import com.teachernavigator.teachernavigator.data.network.adapters.UserDeserializer
import com.teachernavigator.teachernavigator.data.network.requests.*
import com.teachernavigator.teachernavigator.data.network.responses.BaseResponse
import com.teachernavigator.teachernavigator.data.network.responses.GetMyCommentsResponse
import com.teachernavigator.teachernavigator.data.network.responses.PostsResponse
import com.teachernavigator.teachernavigator.data.network.responses.SingInResponse
import com.teachernavigator.teachernavigator.domain.models.*
import com.teachernavigator.teachernavigator.presentation.models.Specialist
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

/**
 * Created by root on 11.08.17
 */
class NetworkController {

    companion object {
        private const val DOMAIN = "pronm.pr-solution.ru"
        public const val HTTP = "http://"
        public const val SERVER = "$HTTP$DOMAIN"
        private const val API_URL = "$SERVER/"

        private const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.Z"
    }

    private val mApiController: ApiController

    private var client: OkHttpClient?

    init {
        Logger.logDebug("created CONTROLLER NetworkController")

        val pureGson = GsonBuilder()
                .setLenient()
                .setDateFormat(DEFAULT_DATE_FORMAT)
                .create()

        val gson = GsonBuilder()
                .setLenient()
                .setDateFormat(DEFAULT_DATE_FORMAT)
                .registerTypeAdapter(Author::class.java, UserDeserializer(pureGson))
                .create()

        val httpClient = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
        }

        client = httpClient.build()
        val retrofit = Retrofit.Builder()
                .baseUrl(API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
        mApiController = retrofit.create(ApiController::class.java)
    }

    // ------------------------------- Auth methods --------------------------------

    fun singUp(request: SingUpRequest): Observable<BaseResponse> = mApiController.singUp(request)


    fun singIn(request: SingInRequest): Observable<SingInResponse> = mApiController.singIn(request)


    fun restorePassword(request: RestorePasswordRequest): Observable<BaseResponse> =
            mApiController.restorePassword(request)

    // ------------------------------- Posts methods --------------------------------

    fun getInterviewsPosts(token: String): Observable<PostsResponse> = mApiController.getPools(token)

    fun getNewsPosts(token: String): Observable<PostsResponse> = mApiController.getNews(token)

    fun getPosts(): Observable<PostsResponse> = mApiController.getPosts()

    fun getPosts(token: String): Observable<PostsResponse> = mApiController.getPosts(token)

    fun getPost(token: String, postId: Int): Observable<PostNetwork> = mApiController.getPost(token, postId)

    fun getMyComments(token: String): Observable<GetMyCommentsResponse> = mApiController.getMyComments(token)

    fun savePost(token: String, request: SavePostRequest): Observable<BaseResponse> = mApiController.savePost(token, request)

    fun getSavedPosts(token: String): Observable<PostsResponse> = mApiController.getSavedPosts(token)

    fun comment(token: String, request: CommentRequest): Observable<CommentNetwork> = mApiController.comment(token, request)

    fun subscribe(token: String, request: SubscribeRequest): Observable<BaseResponse> = mApiController.subscribe(token, request)

    fun getMyPublications(token: String): Observable<Array<PostNetwork>> = mApiController.getMyPublications(token)

    fun getUserPost(token: String, userId: Int): Observable<Array<PostNetwork>> = mApiController.getUserPost(token, userId)

    //fun vote(token: String, request: VoteRequest): Observable<BaseResponse> = mApiController.vote(token, request)

    fun vote(token: String, request: VoteRequest): Observable<BaseResponse> {
        val map = HashMap<String, String>()
        map.put("model", "post")
        map.put("id", request.id.toString())
        map.put("vote", request.like.toString())
        return mApiController.vote(token, map)
    }

    // ------------------------------- Profile methods --------------------------------

    fun getProfile(token: String): Observable<Profile> = mApiController.getProfile(token)

    fun getProfile(token: String, userId: Int): Observable<Profile> = mApiController.getProfile(token, userId)

    fun createVacancy(token: String, vacancyRequest: VacancyRequest): Single<Vacancy> =
            mApiController.createVacancy(token, vacancyRequest)

    fun loadMyVacancies(accessToken: String): Single<List<Vacancy>> =
            mApiController.myVacancies(accessToken)
                    .map { it.results }

    fun loadMyResume(accessToken: String): Single<List<Resume>> =
            mApiController.myResume(accessToken)
                    .map { it.results }

    fun createResume(accessToken: String, resumeRequest: ResumeRequest): Single<Resume> =
            if (resumeRequest.resumePath != null && File(resumeRequest.resumePath).exists()) {
                val file = RequestBody.create(MediaType.parse(resumeRequest.mime), File(resumeRequest.resumePath))
                mApiController.createResumeAndUpload(accessToken, file, resumeRequest.careerObjective, resumeRequest.districtCouncil, resumeRequest.salary,
                        resumeRequest.education, resumeRequest.experience)
            } else {
                mApiController.createResume(accessToken, resumeRequest)
            }

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

}
