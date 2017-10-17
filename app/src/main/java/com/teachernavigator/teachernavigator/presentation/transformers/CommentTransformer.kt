package com.teachernavigator.teachernavigator.presentation.transformers

import android.content.Context
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.data.models.CommentNetwork
import com.teachernavigator.teachernavigator.data.repository.abstractions.IPostsRepository
import com.teachernavigator.teachernavigator.presentation.models.CommentModel
import com.teachernavigator.teachernavigator.presentation.utils.getTimeAgo
import javax.inject.Inject

/**
 * Created by lliepmah on 08.10.17
 */
@PerParentScreen
class CommentTransformer
@Inject
constructor(private val context: Context,
            private val postsRepository: IPostsRepository) : EntityTransformer<CommentNetwork, CommentModel> {

    override fun transform(from: CommentNetwork): CommentModel =
            CommentModel(
                    id = from.id ?: -1,
                    message = from.message ?: "",

                    postAuthorName = from.author?.full_name ?: "",
                    postAuthorAvatar = from.author?.avatar ?: "",

                    isMine = (from.user?.id ?: -1) == postsRepository.currentUserId(),
                    userId = from.user?.id ?: -1,
                    userName = from.user?.full_name.let { if (it == null || it.isBlank()) context.getString(R.string.unknown) else it },
                    userAvatar = from.user?.avatar ?: "",
                    timeAgo = from.created?.getTimeAgo(context) ?: context.getString(R.string.just_now)
            )

}