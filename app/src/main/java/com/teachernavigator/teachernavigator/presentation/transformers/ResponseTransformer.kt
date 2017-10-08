package com.teachernavigator.teachernavigator.presentation.transformers

import com.teachernavigator.teachernavigator.data.network.NetworkController.Companion.HTTP
import com.teachernavigator.teachernavigator.data.network.NetworkController.Companion.SERVER
import com.teachernavigator.teachernavigator.domain.models.Response
import com.teachernavigator.teachernavigator.presentation.models.ResponseModel
import com.teachernavigator.teachernavigator.presentation.utils.getTimeAgo

/**
 * Created by lliepmah on 28.09.17
 */
object ResponseTransformer : EntityTransformer<Response, ResponseModel> {

    override fun transform(from: Response) = ResponseModel(
            portfolio = from.resume.firstOrNull()?.file?.let { if (it.startsWith(HTTP)) it else "$SERVER$it" } ?: "", // hilarious kotlin ☻
            userAvatar = from.employee?.avatars?.firstOrNull()?.avatar ?: "",
            userName = from.employee?.full_name ?: "",
            userId = from.employee?.id ?: -1,
            timeAgo = from.created?.time?.getTimeAgo() ?: ""
    )
}