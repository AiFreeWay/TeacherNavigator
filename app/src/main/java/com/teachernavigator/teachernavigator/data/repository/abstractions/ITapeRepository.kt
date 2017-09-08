package com.teachernavigator.teachernavigator.data.repository.abstractions

import com.teachernavigator.teachernavigator.data.models.CommentNetwork
import com.teachernavigator.teachernavigator.data.models.PostNetwork
import com.teachernavigator.teachernavigator.data.network.requests.CommentRequest
import com.teachernavigator.teachernavigator.data.network.requests.SavePostRequest
import com.teachernavigator.teachernavigator.data.network.requests.VoteRequest
import com.teachernavigator.teachernavigator.data.network.responses.BaseResponse
import com.teachernavigator.teachernavigator.data.network.responses.GetMyCommentsResponse
import io.reactivex.Observable

/**
 * Created by root on 22.08.17.
 */
interface ITapeRepository {

    fun getPosts(): Observable<Array<PostNetwork>>
    fun getNewsPosts(): Observable<Array<PostNetwork>>
    fun getInterviewsPosts(): Observable<Array<PostNetwork>>
    fun getPostById(postId: Int): Observable<PostNetwork>

    fun getMyComments(): Observable<GetMyCommentsResponse>
    fun getSavedPosts(): Observable<Array<PostNetwork>>

    fun savePost(request: SavePostRequest): Observable<BaseResponse>
    fun comment(request: CommentRequest): Observable<CommentNetwork>
    fun vote(request: VoteRequest): Observable<BaseResponse>
}