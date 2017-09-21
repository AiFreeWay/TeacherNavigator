package com.teachernavigator.teachernavigator.data.repository.abstractions

import com.teachernavigator.teachernavigator.data.models.CommentNetwork
import com.teachernavigator.teachernavigator.data.models.PostNetwork
import com.teachernavigator.teachernavigator.data.network.requests.CommentRequest
import com.teachernavigator.teachernavigator.data.network.requests.SavePostRequest
import com.teachernavigator.teachernavigator.data.network.requests.SubscribeRequest
import com.teachernavigator.teachernavigator.data.network.requests.VoteRequest
import com.teachernavigator.teachernavigator.data.network.responses.BaseResponse
import com.teachernavigator.teachernavigator.data.network.responses.GetMyCommentsResponse
import com.teachernavigator.teachernavigator.data.network.responses.PostsResponse
import io.reactivex.Observable

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
    fun comment(request: CommentRequest): Observable<CommentNetwork>
    fun vote(request: VoteRequest): Observable<BaseResponse>
    fun subscribe(request: SubscribeRequest): Observable<BaseResponse>
}