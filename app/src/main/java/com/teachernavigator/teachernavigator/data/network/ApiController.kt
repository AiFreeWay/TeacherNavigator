package com.teachernavigator.teachernavigator.data.network

import com.teachernavigator.teachernavigator.data.models.CommentNetwork
import com.teachernavigator.teachernavigator.data.models.PostCommentNetwork
import com.teachernavigator.teachernavigator.data.models.PostNetwork
import com.teachernavigator.teachernavigator.data.network.requests.*
import com.teachernavigator.teachernavigator.data.network.responses.*
import com.teachernavigator.teachernavigator.domain.models.*
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * Created by root on 11.08.17
 */
interface ApiController {

    // ------------------------------- Auth methods --------------------------------

    @POST("api/v0/sign_up/")
    fun singUp(@Body request: SingUpRequest): Observable<BaseResponse>

    @POST("api/v0/sign_in/")
    fun singIn(@Body request: SingInRequest): Observable<SingInResponse>

    @POST("api/v0/socialauth/convert-token")
    fun convertToken(@Body request: ConvertTokenRequest): Single<SingInResponse>

    @POST("/api/v0/ask_pass/")
    fun restorePassword(@Body request: RestorePasswordRequest): Single<BaseResponse>

    // ------------------------------- Posts methods --------------------------------

    @GET("/api/v0/feed/")
    fun getPosts(@Header("Authorization") accessToken: String?): Single<PostsResponse>

    @GET("/api/v0/feed/best/")
    fun getBestPosts(@Header("Authorization") accessToken: String?): Single<PostsResponse>

    @GET("/api/v0/newses/")
    fun getNews(@Header("Authorization") accessToken: String): Single<PostsResponse>

    @GET("/api/v0/polls/")
    fun getPolls(@Header("Authorization") accessToken: String?): Single<PostsResponse>


    @GET("/api/v0/post/{postId}")
    fun getPost(@Header("Authorization") accessToken: String, @Path("postId") postId: Int): Single<PostNetwork>

    @GET("/api/v0/news/{newsId}")
    fun getNews(@Header("Authorization") accessToken: String, @Path("newsId") newsId: Int): Single<PostNetwork>

    @GET("/api/v0/poll/{pollId}")
    fun getPoll(@Header("Authorization") accessToken: String, @Path("pollId") poll: Int): Single<PostNetwork>

    @FormUrlEncoded
    @POST("/api/v0/save/post/")
    fun savePost(@Header("Authorization") accessToken: String, @Field("post") postId: Int,
                 @Field("user") user: Int = 0): Single<BaseResponse>

    @FormUrlEncoded
    @POST("/api/v0/save/poll/")
    fun savePoll(@Header("Authorization") accessToken: String, @Field("question") postId: Int,
                 @Field("user") user: Int = 0): Single<BaseResponse>

    @FormUrlEncoded
    @POST("/api/v0/save/news/")
    fun saveNews(@Header("Authorization") accessToken: String, @Field("news") postId: Int,
                 @Field("user") user: Int = 0): Single<BaseResponse>

    @GET("/api/v0/me/saved/posts/")
    fun getSavedPosts(@Header("Authorization") accessToken: String): Single<PostsResponse>


    @GET("/api/v0/me/saved/info/")
    fun getSavedImportantInfos(@Header("Authorization") accessToken: String): Single<PostsResponse>


    @GET("/api/v0/me/saved/polls/")
    fun getSavedPolls(@Header("Authorization") accessToken: String): Single<PostsResponse>


    @GET("/api/v0/me/saved/newses/")
    fun getSavedNews(@Header("Authorization") accessToken: String): Single<PostsResponse>

    @GET("/api/v0/me/posts/")
    fun getMyPosts(@Header("Authorization") accessToken: String): Single<BaseListResponse<PostNetwork>>

    @POST("/api/v0/comment/")
    fun comment(@Header("Authorization") accessToken: String, @Body request: CommentRequest): Single<CommentNetwork>

    @POST("/api/v0/me/subscribe/")
    fun subscribe(@Header("Authorization") accessToken: String, @Body request: SubscribeRequest): Single<BaseResponse>

    //@POST("/api/v0/votes/up/")
    //fun vote(@Header("Authorization") accessToken: String, @Body request: VoteRequest): Observable<BaseResponse>

    @GET("/api/v0/votes/up/")
    fun vote(@Header("Authorization") accessToken: String, @QueryMap map: Map<String, String>): Single<BaseResponse>

    @GET("/api/v0/profile/{userId}/posts/ ")
    fun getUserPost(@Header("Authorization") accessToken: String, @Path("userId") userId: Int): Single<Array<PostNetwork>>

    // ------------------------------- Profile methods --------------------------------

    @GET("/api/v0/me/")
    fun getProfile(@Header("Authorization") accessToken: String): Single<Profile>

    @GET("/api/v0/profile/{userId}/")
    fun getProfile(@Header("Authorization") accessToken: String, @Path("userId") userId: Int): Single<Profile>

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
    fun askSpecialist(@Header("Authorization") accessToken: String, @Field("expert") name: Int, @Field("text") question: String): Maybe<Unit>

    @GET("/api/v0/info_posts/{themeId}/")
    fun getInfoPosts(@Header("Authorization") accessToken: String, @Path("themeId") themeId: Int): Single<PostsResponse>

    @GET("/api/v0/info_post/{postId}/")
    fun getInfoPost(@Header("Authorization") accessToken: String, @Path("postId") postId: Int): Single<PostNetwork>

    @FormUrlEncoded
    @POST("/api/v0/save/info/")
    fun saveInfoPost(@Header("Authorization") accessToken: String, @Field("important_info") postId: Int,
                     @Field("user") user: Int = 0): Single<Unit>

    @GET("/api/v0/tags/")
    fun tags(@Header("Authorization") accessToken: String, @Query("page") page: Int): Observable<BaseListResponse<Tag>>

    @GET("/api/v0/me/comments/")
    fun myComments(@Header("Authorization") accessToken: String): Single<BaseListResponse<PostCommentNetwork>>

    @GET("/api/v0/tags/trends")
    fun tagsTrends(@Header("Authorization") accessToken: String, @Query("page") page: Int): Observable<BaseListResponse<Tag>>

    @Multipart
    @POST("/api/v0/post/")
    fun sendPost(@Header("Authorization") accessToken: String,
                 @PartMap params: Map<String, @JvmSuppressWildcards RequestBody>): Single<PostNetwork>

    @FormUrlEncoded
    @POST("/api/v0/me/complaint/info/")
    fun complaintInfo(@Header("Authorization") accessToken: String, @Field("important_info") postId: Int): Single<BaseResponse>

    @FormUrlEncoded
    @POST("/api/v0/me/complaint/post/")
    fun complaintPost(@Header("Authorization") accessToken: String, @Field("post") postId: Int): Single<BaseResponse>

    @FormUrlEncoded
    @POST("/api/v0/me/complaint/news/")
    fun complaintNews(@Header("Authorization") accessToken: String, @Field("news") postId: Int): Single<BaseResponse>

    @FormUrlEncoded
    @POST("/api/v0/me/complaint/poll/")
    fun complaintPoll(@Header("Authorization") accessToken: String, @Field("question") postId: Int): Single<BaseResponse>

    @FormUrlEncoded
    @POST("/api/v0/polls/action/")
    fun passPoll(@Header("Authorization") accessToken: String, @Field("question") postId: Int, @Field("choice") choiceId: Int): Single<BaseResponse>


}