package com.teachernavigator.teachernavigator.presentation.models

/**
 * Created by Arthur Korchagin on 27.10.17
 */
data class MessageModel(
        val id: Int,
        val prevId: Int,
        val dateLong: Long,
        val dateString: String,
        val timeString: String,
        val text: String,
        val isMine: Boolean,
        val userName: String,
        val userAvatar: String,
        val userId: Int
)