package com.teachernavigator.teachernavigator.presentation.facades.abstractions

/**
 * Created by root on 08.09.17.
 */
interface IPostControllerFacadeCallback {

    fun onLike()
    fun onDislike()
    fun onSave()
    fun onSubscribe()
    fun onComplain()

    fun onError(error: Throwable)
}