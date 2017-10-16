package com.teachernavigator.teachernavigator.data.models

/**
 * Created by Arthur Korchagin on 16.10.17
 */
data class PostCommentNetwork(

        val post: PostNetwork? = null,
        val poll: PostNetwork? = null,
        val news: PostNetwork? = null,
        val important_info: PostNetwork? = null

) : CommentNetwork()