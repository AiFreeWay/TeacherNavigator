package com.teachernavigator.teachernavigator.presentation.utils

import com.teachernavigator.teachernavigator.domain.interactors.abstractions.ITapeInteractor
import com.teachernavigator.teachernavigator.domain.models.Post
import io.reactivex.Observable

/**
 * Created by root on 22.08.17.
 */
class TapeStrategy {

    companion object {

        val TAPE_TYPE_BEST = 0
        val TAPE_TYPE_INTERVIEWS = 1
        val TAPE_TYPE_LAST = 2
        val TAPE_TYPE_NEWS = 3

        fun getPostByType(tapeType: Int, interactor: ITapeInteractor): Observable<List<Post>> {
            return when(tapeType) {
                TAPE_TYPE_BEST -> interactor.getBestPosts()
                TAPE_TYPE_INTERVIEWS -> interactor.getInterviewsPosts()
                TAPE_TYPE_LAST -> interactor.getLastPosts()
                TAPE_TYPE_NEWS -> interactor.getNewsPosts()
                else -> throw Exception("Invalid tape type $tapeType TapeStrategy.getPostByType(tapeType: Int, interactor: ITapeInteractor))")
            }
        }
    }
}