package com.teachernavigator.teachernavigator.data.network

import com.teachernavigator.teachernavigator.data.models.PostNetwork
import com.teachernavigator.teachernavigator.data.network.requests.RestorePasswordRequest
import com.teachernavigator.teachernavigator.data.network.requests.SingInRequest
import com.teachernavigator.teachernavigator.data.network.requests.SingUpRequest
import com.teachernavigator.teachernavigator.data.network.responses.BaseResponse
import com.teachernavigator.teachernavigator.data.network.responses.SingInResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by root on 11.08.17.
 */
interface ApiController {

    // ------------------------------- Auth methods --------------------------------

    @POST("api/v0/sign_up/")
    fun singUp(@Body request: SingUpRequest): Observable<BaseResponse>

    @POST("api/v0/sign_in/")
    fun singIn(@Body request: SingInRequest): Observable<SingInResponse>

    @POST("/api/v0/ask_pass/")
    fun restorePassword(@Body request: RestorePasswordRequest): Observable<BaseResponse>

    // ------------------------------- Posts methods --------------------------------

    @GET("/api/v0/feed/")
    fun getPosts(): Observable<Array<PostNetwork>>

    @GET("/api/v0/post/{postId}")
    fun getPost(@Path("postId") postId: Int): Observable<PostNetwork>
}