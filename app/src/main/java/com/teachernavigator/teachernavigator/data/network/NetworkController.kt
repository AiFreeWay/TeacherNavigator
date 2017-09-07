package com.teachernavigator.teachernavigator.data.network

import com.example.root.androidtest.application.utils.Logger
import com.google.gson.GsonBuilder
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.data.models.PostNetwork
import com.teachernavigator.teachernavigator.data.network.requests.RestorePasswordRequest
import com.teachernavigator.teachernavigator.data.network.requests.SingInRequest
import com.teachernavigator.teachernavigator.data.network.requests.SingUpRequest
import com.teachernavigator.teachernavigator.data.network.responses.BaseResponse
import com.teachernavigator.teachernavigator.data.network.responses.SingInResponse
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
        if (BuildConfig.DEBUG) Logger.logDebug("created CONTROLLER NetworkController")

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

    fun getBestPosts(): Observable<Array<PostNetwork>> = Observable.just(emptyArray())

    fun getInterviewsPosts(): Observable<Array<PostNetwork>> = Observable.just(emptyArray())

    fun getLatestPosts(): Observable<Array<PostNetwork>> = Observable.just(emptyArray())

    fun getNewsPosts(): Observable<Array<PostNetwork>> = mApiController.getPosts()

    fun getPost(postId: Int): Observable<PostNetwork> = mApiController.getPost(postId)
}
