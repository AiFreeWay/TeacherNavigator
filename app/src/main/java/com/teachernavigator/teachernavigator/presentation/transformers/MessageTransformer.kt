package com.teachernavigator.teachernavigator.presentation.transformers

import android.content.Context
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.data.repository.abstractions.IPostsRepository
import com.teachernavigator.teachernavigator.domain.models.Message
import com.teachernavigator.teachernavigator.presentation.models.MessageModel
import com.teachernavigator.teachernavigator.presentation.utils.formatDisplayDate
import com.teachernavigator.teachernavigator.presentation.utils.formatDisplayTime
import javax.inject.Inject

/**
 * Created by lliepmah on 08.10.17
 */
@PerParentScreen
class MessageTransformer
@Inject
constructor(private val context: Context,
            private val postsRepository: IPostsRepository) : EntityTransformer<Message, MessageModel> {

    override fun transform(from: Message): MessageModel =
            MessageModel(

                    id = from.id,
                    text = from.text ?: "",
                    userName = from.user?.full_name.let { if (it == null || it.isBlank()) context.getString(R.string.unknown) else it },
                    userAvatar = from.user?.avatar ?: "",
                    isMine = (from.user?.id ?: -1) == postsRepository.currentUserId(),
                    userId = from.user?.id ?: -1,
                    dateLong = from.created?.time ?: 0,
                    dateString = from.created?.formatDisplayDate ?: "",
                    timeString = from.created?.formatDisplayTime ?: "",
                    prevId = from.prev_id

            )

}