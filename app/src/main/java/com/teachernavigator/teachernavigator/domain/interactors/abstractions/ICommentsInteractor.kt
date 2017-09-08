package com.teachernavigator.teachernavigator.domain.interactors.abstractions

import com.teachernavigator.teachernavigator.domain.models.Comment
import io.reactivex.Observable

/**
 * Created by root on 08.09.17.
 */
interface ICommentsInteractor {

    fun getMyComments(): Observable<List<Comment>>
}