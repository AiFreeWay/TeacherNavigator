package com.teachernavigator.teachernavigator.presentation.facades.abstractions

/**
 * Created by root on 13.09.17.
 */
interface ICommentControllerFacadeCallback {

    fun onSubscribe()

    fun onError(error: Throwable)
}