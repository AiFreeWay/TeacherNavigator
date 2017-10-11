package com.teachernavigator.teachernavigator.data.repository.abstractions

import com.teachernavigator.teachernavigator.data.models.CommentNetwork
import com.teachernavigator.teachernavigator.data.models.FileInfo
import com.teachernavigator.teachernavigator.data.models.PostNetwork
import com.teachernavigator.teachernavigator.data.network.requests.CommentRequest
import com.teachernavigator.teachernavigator.data.network.requests.SavePostRequest
import com.teachernavigator.teachernavigator.data.network.requests.SubscribeRequest
import com.teachernavigator.teachernavigator.data.network.requests.VoteRequest
import com.teachernavigator.teachernavigator.data.network.responses.BaseResponse
import com.teachernavigator.teachernavigator.data.network.responses.GetMyCommentsResponse
import com.teachernavigator.teachernavigator.data.network.responses.PostsResponse
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.domain.models.PostType
import com.teachernavigator.teachernavigator.domain.models.Tag
import com.teachernavigator.teachernavigator.presentation.models.Info
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by root on 22.08.17.
 */
interface ITapeRepository {

    fun getPosts(): Observable<PostsResponse>
    fun getNewsPosts(): Observable<PostsResponse>
    fun getInterviewsPosts(): Observable<PostsResponse>
    fun getPostById(postId: Int): Observable<PostNetwork>

    fun getMyComments(): Observable<GetMyCommentsResponse>
    fun getSavedPosts(): Observable<PostsResponse>
    fun getMyPublications(): Observable<Array<PostNetwork>>
    fun getUserPost(userId: Int): Observable<Array<PostNetwork>>

    fun savePost(request: SavePostRequest): Observable<BaseResponse>
    fun comment(request: CommentRequest): Single<CommentNetwork>
    fun vote(request: VoteRequest): Single<BaseResponse>
    fun subscribe(request: SubscribeRequest): Observable<BaseResponse>
    fun getInfoPosts(currentTheme: Info): Single<PostsResponse>
    fun getPost(postId: Int, postType: PostType): Single<PostNetwork>

    fun save(postId: Int, type: PostType): Single<Unit>
    fun getTags(): Single<List<Tag>>
    fun getTrends(): Single<List<Tag>>

    fun sendPost(title: String, text: String, tags: List<String>, fileInfo: FileInfo?): Single<Post>
}