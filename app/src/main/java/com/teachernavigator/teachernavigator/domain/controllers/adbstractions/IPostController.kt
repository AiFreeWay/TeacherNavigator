package com.teachernavigator.teachernavigator.domain.controllers

import android.app.Activity
import com.teachernavigator.teachernavigator.domain.models.Comment
import com.teachernavigator.teachernavigator.domain.models.Monade
import com.teachernavigator.teachernavigator.domain.models.Post
import io.reactivex.Observable

/**
 * Created by root on 07.09.17.
 */
interface IPostController {

    fun like(vote: Boolean, post: Post, doOnUserNotAuth: () -> Unit): Observable<Monade>
    fun save(post: Post, doOnUserNotAuth: () -> Unit): Observable<Monade>
    fun subscribe(post: Post, doOnUserNotAuth: () -> Unit): Observable<Monade>
    fun subscribe(comment: Comment, doOnUserNotAuth: () -> Unit): Observable<Monade>
    fun complain(post: Post, doOnUserNotAuth: () -> Unit): Observable<Monade>

    fun openCommentsScreen(post: Post, activity: Activity, doOnUserNotAuth: () -> Unit): Observable<Monade>
    fun openProfileScreen(post: Post, activity: Activity, doOnUserNotAuth: () -> Unit): Observable<Monade>
    fun openProfileScreen(comment: Comment, activity: Activity, doOnUserNotAuth: () -> Unit): Observable<Monade>
    fun openBranch(comment: Comment, activity: Activity)
    fun openPostDetailScreen(post: Post, activity: Activity)
}