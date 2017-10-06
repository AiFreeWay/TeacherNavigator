package com.teachernavigator.teachernavigator.data.network

import com.teachernavigator.teachernavigator.data.models.CommentNetwork
import com.teachernavigator.teachernavigator.data.models.PostNetwork
import com.teachernavigator.teachernavigator.data.network.requests.*
import com.teachernavigator.teachernavigator.data.network.responses.*
import com.teachernavigator.teachernavigator.domain.models.About
import com.teachernavigator.teachernavigator.domain.models.Profile
import com.teachernavigator.teachernavigator.domain.models.Resume
import com.teachernavigator.teachernavigator.domain.models.Vacancy
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.RequestBody
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
    fun getPosts(): Observable<PostsResponse>

    @GET("/api/v0/feed/")
    fun getPosts(@Header("Authorization") accessToken: String): Observable<PostsResponse>

    @GET("/api/v0/newses/")
    fun getNews(@Header("Authorization") accessToken: String): Observable<PostsResponse>

    @GET("/api/v0/polls/")
    fun getPools(@Header("Authorization") accessToken: String): Observable<PostsResponse>

    @GET("/api/v0/post/{postId}")
    fun getPost(@Header("Authorization") accessToken: String, @Path("postId") postId: Int): Observable<PostNetwork>

    @GET("/api/v0/me/comments/")
    fun getMyComments(@Header("Authorization") accessToken: String): Observable<GetMyCommentsResponse>

    @POST("/api/v0/save/post/")
    fun savePost(@Header("Authorization") accessToken: String, @Body request: SavePostRequest): Observable<BaseResponse>

    @GET("/api/v0/me/saved/")
    fun getSavedPosts(@Header("Authorization") accessToken: String): Observable<PostsResponse>

    @POST("/api/v0/comment/")
    fun comment(@Header("Authorization") accessToken: String, @Body request: CommentRequest): Observable<CommentNetwork>

    @POST("/api/v0/me/subscribe/")
    fun subscribe(@Header("Authorization") accessToken: String, @Body request: SubscribeRequest): Observable<BaseResponse>

    //@POST("/api/v0/votes/up/")
    //fun vote(@Header("Authorization") accessToken: String, @Body request: VoteRequest): Observable<BaseResponse>

    @GET("/api/v0/votes/up/")
    fun vote(@Header("Authorization") accessToken: String, @QueryMap map: Map<String, String>): Observable<BaseResponse>

    @GET("/api/v0/me/posts/")
    fun getMyPublications(@Header("Authorization") accessToken: String): Observable<Array<PostNetwork>>

    @GET("/api/v0/profile/{userId}/posts/ ")
    fun getUserPost(@Header("Authorization") accessToken: String, @Path("userId") userId: Int): Observable<Array<PostNetwork>>

    // ------------------------------- Profile methods --------------------------------

    @GET("/api/v0/me/")
    fun getProfile(@Header("Authorization") accessToken: String): Observable<Profile>

    @GET("/api/v0/profile/{userId}/")
    fun getProfile(@Header("Authorization") accessToken: String, @Path("userId") userId: Int): Observable<Profile>

    @POST("/api/v0/me/vacancy/")
    fun createVacancy(@Header("Authorization") accessToken: String, @Body vacancyRequest: VacancyRequest): Single<Vacancy>

    @GET("/api/v0/me/vacancies/")
    fun myVacancies(@Header("Authorization") accessToken: String): Single<BaseListResponse<Vacancy>>

    @GET("/api/v0/me/resumes/")
    fun myResume(@Header("Authorization") accessToken: String): Single<BaseListResponse<Resume>>

    @GET("/api/v0/resumes/")
    fun resumeList(@Header("Authorization") accessToken: String): Single<BaseListResponse<Resume>>

    @POST("/api/v0/me/resume/")
    fun createResume(@Header("Authorization") accessToken: String, @Body resumeRequest: ResumeRequest): Single<Resume>

    @Multipart
    @POST("/api/v0/me/resume/")
    fun createResumeAndUpload(@Header("Authorization") accessToken: String,
                              @Part("file\"; filename=\"file.jpg") file: RequestBody,
                              @Part("career_objective") career_objective: String,
                              @Part("district_council") district_council: Int,
                              @Part("amount_of_wages") amount_of_wages: String,
                              @Part("education") education: String,
                              @Part("experience") experience: String
    ): Single<Resume>

    @GET("/api/v0/vacancies/")
    fun vacancies(@Header("Authorization") accessToken: String): Single<BaseListResponse<Vacancy>>

    @GET("/api/v0/vacancy/{vacancyId}")
    fun vacancy(@Header("Authorization") accessToken: String, @Path("vacancyId") vacancyId: Int): Single<Vacancy>

    @DELETE("/api/v0/vacancy/{vacancyId}/")
    fun removeVacancy(@Header("Authorization") accessToken: String, @Path("vacancyId") vacancyId: Int): Maybe<Unit>

    @PATCH("/api/v0/me/vacancy/update/{vacancyId}/")
    fun updateVacancy(@Header("Authorization") accessToken: String, @Path("vacancyId") vacancyId: Int): Maybe<Unit>

    @PATCH("/api/v0/me/resume/update/{resumeId}/")
    fun updateResume(@Header("Authorization") accessToken: String, @Path("resumeId") resumeId: Int): Maybe<Unit>

    @DELETE("/api/v0/me/resume/{resumeId}/")
    fun removeResume(@Header("Authorization") accessToken: String, @Path("resumeId") resumeId: Int): Maybe<Unit>

    @POST("/api/v0/response/")
    fun respondVacancy(@Header("Authorization") accessToken: String, @Body request: RespondVacancyRequest): Maybe<Unit>

    @GET("/api/v0/about/")
    fun loadAbout(@Header("Authorization") accessToken: String): Single<BaseListResponse<About>>

    @FormUrlEncoded
    @POST("/api/v0/me/support/")
    fun askSpecialist(@Header("Authorization") accessToken: String, @Field("expert") name: String, @Field("text") question: String): Maybe<Unit>

}