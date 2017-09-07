package com.teachernavigator.teachernavigator.presentation.utils

import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IPostsInteractor
import com.teachernavigator.teachernavigator.domain.models.Post
import io.reactivex.Observable

/**
 * Created by root on 22.08.17.
 */
class TapeStrategy {

    companion object {

        val POSTS_TYPE_BEST = 0
        val POSTS_TYPE_INTERVIEWS = 1
        val POSTS_TYPE_LAST = 2
        val POSTS_TYPE_NEWS = 3

        fun getPostByType(tapeType: Int, interactor: IPostsInteractor): Observable<List<Post>> {
            return when(tapeType) {
                POSTS_TYPE_BEST -> interactor.getBestPosts()
                POSTS_TYPE_INTERVIEWS -> interactor.getInterviewsPosts()
                POSTS_TYPE_LAST -> interactor.getLatestPosts()
                POSTS_TYPE_NEWS -> interactor.getNewsPosts()
                else -> throw Exception("Invalid tape type $tapeType TapeStrategy.getPostByType(tapeType: Int, interactor: IPostsInteractor))")
            }
        }
    }
}