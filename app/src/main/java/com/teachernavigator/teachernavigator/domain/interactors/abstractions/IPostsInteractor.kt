package com.teachernavigator.teachernavigator.domain.interactors.abstractions

import com.teachernavigator.teachernavigator.data.models.CommentNetwork
import com.teachernavigator.teachernavigator.data.models.FileInfo
import com.teachernavigator.teachernavigator.data.models.PostNetwork
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.domain.models.PostType
import com.teachernavigator.teachernavigator.domain.models.Tag
import com.teachernavigator.teachernavigator.presentation.models.Info
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by root on 22.08.17
 */
interface IPostsInteractor {

    fun getBestPosts(): Observable<List<Post>>
    fun getInterviewsPosts(): Observable<List<Post>>
    fun getLatestPosts(): Observable<List<Post>>
    fun getNewsPosts(): Observable<List<Post>>
    fun getUserPost(userId: Int): Observable<List<Post>>
    fun getPostById(postId: Int): Observable<Post>
    fun getSavedPosts(): Observable<List<Post>>
    fun getMyPublications(): Observable<List<Post>>

    fun getInfoPosts(currentTheme: Info): Single<List<PostNetwork>>
    fun getPost(postId: Int, postType: PostType): Single<PostNetwork>
    fun sendComment(postId: Int, postType: PostType, text: String): Single<CommentNetwork>
    fun getTags(): Single<List<Tag>>
    fun getTrends(): Single<List<Tag>>

    fun sendPost(title: String, text: String, tags: List<String>,fileInfo: FileInfo?): Single<Post>
}