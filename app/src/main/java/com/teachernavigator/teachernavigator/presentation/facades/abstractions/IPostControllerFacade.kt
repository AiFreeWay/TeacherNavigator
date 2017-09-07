package com.teachernavigator.teachernavigator.presentation.facades.abstractions

import com.teachernavigator.teachernavigator.domain.models.Monade
import com.teachernavigator.teachernavigator.domain.models.Post
import io.reactivex.Observable

/**
 * Created by root on 07.09.17.
 */
interface IPostControllerFacade {

    fun like(post: Post): Observable<Monade>
    fun dislike(post: Post): Observable<Monade>
    fun save(post: Post): Observable<Monade>
    fun subscribe(post: Post): Observable<Monade>
    fun complain(post: Post): Observable<Monade>

    fun openCommentsScreen(post: Post)
    fun openProfileScreen(post: Post)
    fun openPostDetailScreen(post: Post)
}