package com.teachernavigator.teachernavigator.data.network

import com.teachernavigator.teachernavigator.data.models.CommentNetwork
import com.teachernavigator.teachernavigator.data.models.PostNetwork
import com.teachernavigator.teachernavigator.data.network.requests.*
import com.teachernavigator.teachernavigator.data.network.responses.BaseResponse
import com.teachernavigator.teachernavigator.data.network.responses.GetMyCommentsResponse
import com.teachernavigator.teachernavigator.data.network.responses.SingInResponse
import io.reactivex.Observable
import retrofit2.http.*

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

    @GET("/api/v0/me/comments/")
    fun getMyComments(@Header("Authorization") accessToken: String): Observable<GetMyCommentsResponse>

    @POST("/api/v0/save/post/")
    fun savePost(@Header("Authorization") accessToken: String, @Body request: SavePostRequest): Observable<BaseResponse>

    @GET("/api/v0/me/saved/")
    fun getSavedPosts(@Header("Authorization") accessToken: String): Observable<Array<PostNetwork>>

    @POST("/api/v0/comment/")
    fun comment(@Header("Authorization") accessToken: String, @Body request: CommentRequest): Observable<CommentNetwork>

    //@POST("/api/v0/votes/up/")
    //fun vote(@Header("Authorization") accessToken: String, @Body request: VoteRequest): Observable<BaseResponse>

    @GET("/api/v0/votes/up/")
    fun vote(@Header("Authorization") accessToken: String, @QueryMap map: Map<String, String>): Observable<BaseResponse>
}