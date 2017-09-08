package com.teachernavigator.teachernavigator.domain.interactors.abstractions

import com.teachernavigator.teachernavigator.domain.models.Comment
import com.teachernavigator.teachernavigator.domain.models.Post
import io.reactivex.Observable

/**
 * Created by root on 08.09.17.
 */
interface ICommentsInteractor {

    fun getMyComments(): Observable<List<Comment>>
    fun comment(post: Post, text: String): Observable<Comment>
}