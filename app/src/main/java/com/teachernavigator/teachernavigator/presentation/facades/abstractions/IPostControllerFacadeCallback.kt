package com.teachernavigator.teachernavigator.presentation.facades.abstractions

import com.teachernavigator.teachernavigator.domain.models.Monade

/**
 * Created by root on 08.09.17.
 */
interface IPostControllerFacadeCallback {

    fun onLike(result: Monade)
    fun onDislike(result: Monade)
    fun onSave(result: Monade)
    fun onSubscribe(result: Monade)
    fun onComplain(result: Monade)

    fun onError(error: Throwable)
}