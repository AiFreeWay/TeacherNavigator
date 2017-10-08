package com.teachernavigator.teachernavigator.presentation.transformers

import android.content.Context
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.data.models.CommentNetwork
import com.teachernavigator.teachernavigator.presentation.models.CommentModel
import com.teachernavigator.teachernavigator.presentation.utils.getTimeAgo
import javax.inject.Inject

/**
 * Created by lliepmah on 08.10.17
 */
@PerParentScreen
class CommentTransformer
@Inject
constructor(context: Context) : EntityTransformer<CommentNetwork, CommentModel> {

    override fun transform(from: CommentNetwork): CommentModel =
            CommentModel(
                    id = from.id ?: -1,
                    message = from.message ?: "",

                    postAuthorName = from.author?.full_name ?: "",
                    postAuthorAvatar = from.author?.avatars?.firstOrNull()?.avatar ?: "",

                    userName = from.user?.full_name ?: "",
                    userAvatar = from.user?.avatars?.avatar ?: "",
                    timeAgo = from.created?.getTimeAgo() ?: ""
            )

}