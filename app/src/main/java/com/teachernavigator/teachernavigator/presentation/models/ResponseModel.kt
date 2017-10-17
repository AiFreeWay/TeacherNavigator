package com.teachernavigator.teachernavigator.presentation.models

/**
 * Created by lliepmah on 28.09.17
 */
data class ResponseModel(
        override val id: Int = -1,
        val userId: Int,
        val userName: String,
        val userAvatar: String,
        val portfolio: String,
        val timeAgo: String,
        val isMine: Boolean
) : Model(id)