package com.teachernavigator.teachernavigator.data.network

import com.example.root.androidtest.application.utils.Logger
import com.google.gson.GsonBuilder
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.data.models.CommentNetwork
import com.teachernavigator.teachernavigator.data.models.PostNetwork
import com.teachernavigator.teachernavigator.data.network.requests.*
import com.teachernavigator.teachernavigator.data.network.responses.*
import com.teachernavigator.teachernavigator.domain.models.Profile
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by root on 11.08.17.
 */
class NetworkController {

    private val API_URL = "http://pronm.pr-solution.ru/"
    private val mApiController: ApiController

    init {
        Logger.logDebug("created CONTROLLER NetworkController")

        val gson = GsonBuilder()
                .setLenient()
                .create()

        val httpClient = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
        }

        val retrofit = Retrofit.Builder()
                .baseUrl(API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
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
}
