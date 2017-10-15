package com.teachernavigator.teachernavigator.data.repository.abstractions

import com.teachernavigator.teachernavigator.data.models.CommentNetwork
import com.teachernavigator.teachernavigator.data.models.FileInfo
import com.teachernavigator.teachernavigator.data.models.PostNetwork
import com.teachernavigator.teachernavigator.data.network.requests.CommentRequest
import com.teachernavigator.teachernavigator.data.network.responses.BaseResponse
import com.teachernavigator.teachernavigator.data.network.responses.GetMyCommentsResponse
import com.teachernavigator.teachernavigator.data.network.responses.PostsResponse
import com.teachernavigator.teachernavigator.domain.models.PostType
import com.teachernavigator.teachernavigator.domain.models.Tag
import com.teachernavigator.teachernavigator.presentation.models.Info
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by root on 22.08.17
 */

interface IPostsRepository {

    fun getPosts(): Single<PostsResponse>
    fun getBestPosts(): Single<PostsResponse>
    fun getNewsPosts(): Single<PostsResponse>
    fun getPolls(): Single<PostsResponse>

    fun getSavedPosts(): Single<PostsResponse>
    fun getMyPublications(): Single<Array<PostNetwork>>
    fun getUserPost(userId: Int): Single<Array<PostNetwork>>

    fun getMyComments(): Observable<GetMyCommentsResponse>

    fun comment(request: CommentRequest): Single<CommentNetwork>

    fun vote(postId: Int, isLike: Boolean, type: PostType): Single<BaseResponse>
    fun complaint(postId: Int, type: PostType): Single<BaseResponse>

    fun subscribe(authorId: Int): Single<BaseResponse>

    fun getInfoPosts(currentTheme: Info): Single<PostsResponse>
    fun getPost(postId: Int, postType: PostType): Single<PostNetwork>

    fun save(postId: Int, type: PostType): Single<Unit>
    fun getTags(): Single<List<Tag>>
    fun getTrends(): Single<List<Tag>>

    fun sendPost(title: String, text: String, tags: List<String>, fileInfo: FileInfo?): Single<PostNetwork>

    /* Storage Methods */
    fun isPollPassed(id: Int): Boolean

    fun sedPollPassed(id: Int)
    fun passPoll(postId: Int, choiceId: Int): Single<BaseResponse>
}