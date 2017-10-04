package com.teachernavigator.teachernavigator.presentation.transformers

import android.text.format.DateUtils
import com.teachernavigator.teachernavigator.data.network.NetworkController.Companion.SERVER
import com.teachernavigator.teachernavigator.data.network.NetworkController.Companion.HTTP
import com.teachernavigator.teachernavigator.domain.models.Response
import com.teachernavigator.teachernavigator.presentation.models.ResponseModel
import com.teachernavigator.teachernavigator.presentation.utils.getTimeAgo

/**
 * Created by lliepmah on 28.09.17
 */
object ResponseTransformer : EntityTransformer<Response, ResponseModel> {
    private var idIncrementor = 1 //TODO Temporary hack

    override fun transform(from: Response) = ResponseModel(
            id = idIncrementor++,
            portfolio = from.resume.firstOrNull()?.file?.let { if (it.startsWith(HTTP)) it else "$SERVER$it" } ?: "", // hilarious kotlin ☻
            userAvatar = from.employee?.avatars?.firstOrNull()?.avatar ?: "",
            userName = from.employee?.full_name ?: "",
//            timeAgo = DateUtils.formatElapsedTime(System.currentTimeMillis() - (from.created?.time ?: System.currentTimeMillis()))
            timeAgo = getTimeAgo(from.created?.time ?: 0)
    )
}