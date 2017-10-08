package com.teachernavigator.teachernavigator.data.network.requests

import com.teachernavigator.teachernavigator.domain.models.PostType

/**
 * Created by root on 08.09.17.
 */
@Suppress("DataClassPrivateConstructor")
data class CommentRequest
private constructor(
        val post: Int? = null,
        val poll: Int? = null,
        val news: Int? = null,
        val important_info: Int? = null,
        val message: String
) {

    companion object {
        fun build(id: Int, postType: PostType, message: String) = when (postType) {
            PostType.post -> CommentRequest(post = id, message = message)
            PostType.news -> CommentRequest(news = id, message = message)
            PostType.question -> CommentRequest(poll = id, message = message)
            PostType.importantinfo -> CommentRequest(important_info = id, message = message)
        }
    }
}