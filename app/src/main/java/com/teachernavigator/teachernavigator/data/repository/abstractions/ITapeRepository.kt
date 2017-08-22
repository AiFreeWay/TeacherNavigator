package com.teachernavigator.teachernavigator.data.repository.abstractions

import com.teachernavigator.teachernavigator.domain.models.Post
import io.reactivex.Observable

/**
 * Created by root on 22.08.17.
 */
interface ITapeRepository {

    fun getBestPosts(): Observable<List<Post>>
    fun getInterviewsPosts(): Observable<List<Post>>
    fun getLastPosts(): Observable<List<Post>>
    fun getNewsPosts(): Observable<List<Post>>
}