package com.teachernavigator.teachernavigator.presentation.transformers

import android.content.Context
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.data.network.NetworkController.Companion.HTTP
import com.teachernavigator.teachernavigator.data.network.NetworkController.Companion.SERVER
import com.teachernavigator.teachernavigator.domain.models.Response
import com.teachernavigator.teachernavigator.presentation.models.ResponseModel
import com.teachernavigator.teachernavigator.presentation.utils.getTimeAgo
import javax.inject.Inject

/**
 * Created by lliepmah on 28.09.17
 */
@PerParentScreen
class ResponseTransformer
@Inject
constructor(private val context: Context) : EntityTransformer<Response, ResponseModel> {

    override fun transform(from: Response) = ResponseModel(
            portfolio = from.resume.firstOrNull()?.file?.let { if (it.startsWith(HTTP)) it else "$SERVER$it" } ?: "", // hilarious kotlin ☻
            userAvatar = from.employee?.avatar ?: "",
            userName = from.employee?.full_name ?: "",
            userId = from.employee?.id ?: -1,
            timeAgo = from.created?.time?.getTimeAgo(context) ?: context.getString(R.string.just_now)
    )
}