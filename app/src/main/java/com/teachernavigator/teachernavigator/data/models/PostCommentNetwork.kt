package com.teachernavigator.teachernavigator.data.models

/**
 * Created by Arthur Korchagin on 16.10.17
 */
data class PostCommentNetwork(

        var post: Int? = null,
        var poll: Int? = null,
        var news: Int? = null,
        var important_info: Int? = null,

        var postNetwork: PostNetwork? = null

) : CommentNetwork()