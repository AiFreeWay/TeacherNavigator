package com.teachernavigator.teachernavigator.domain.interactors.abstractions

import com.teachernavigator.teachernavigator.domain.models.Post
import io.reactivex.Observable

/**
 * Created by root on 22.08.17.
 */
interface IPostsInteractor {

    fun getBestPosts(): Observable<List<Post>>
    fun getInterviewsPosts(): Observable<List<Post>>
    fun getLatestPosts(): Observable<List<Post>>
    fun getNewsPosts(): Observable<List<Post>>
    fun getPostById(postId: Int): Observable<Post>
}