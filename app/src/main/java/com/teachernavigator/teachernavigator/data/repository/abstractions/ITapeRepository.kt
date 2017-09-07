package com.teachernavigator.teachernavigator.data.repository.abstractions

import com.teachernavigator.teachernavigator.data.models.PostNetwork
import io.reactivex.Observable

/**
 * Created by root on 22.08.17.
 */
interface ITapeRepository {

    fun getBestPosts(): Observable<Array<PostNetwork>>
    fun getInterviewsPosts(): Observable<Array<PostNetwork>>
    fun getLatestPosts(): Observable<Array<PostNetwork>>
    fun getNewsPosts(): Observable<Array<PostNetwork>>
    fun getPostById(postId: Int): Observable<PostNetwork>
}