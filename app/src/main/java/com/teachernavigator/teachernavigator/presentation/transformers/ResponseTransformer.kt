package com.teachernavigator.teachernavigator.presentation.transformers

import com.teachernavigator.teachernavigator.domain.models.Response
import com.teachernavigator.teachernavigator.presentation.models.ResponseModel

/**
 * Created by lliepmah on 28.09.17
 */
object ResponseTransformer : EntityTransformer<Response, ResponseModel> {
    private var idIncrementor = 1 //TODO Temporary hack

    override fun transform(from: Response) = ResponseModel(
            id = idIncrementor++,
            portfolio = "", //TODO get it from Response
            userAvatar = from.employee?.avatars?.firstOrNull()?.avatar ?: "",
            userName = from.employee?.full_name ?: "",
            timeAgo = "35 минут назад" //TODO Calc it
    )
}