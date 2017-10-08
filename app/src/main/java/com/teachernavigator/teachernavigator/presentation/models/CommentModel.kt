package com.teachernavigator.teachernavigator.presentation.models

/**
 * Created by lliepmah on 07.10.17
 */
data class CommentModel(
        override val id: Int,
        var message: String,

        var postAuthorName: String,
        var postAuthorAvatar: String,

        var userName: String,
        var userAvatar: String,
        val timeAgo: String

) : Model(id)