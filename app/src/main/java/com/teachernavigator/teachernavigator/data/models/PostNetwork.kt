package com.teachernavigator.teachernavigator.data.models

import com.teachernavigator.teachernavigator.domain.models.Author
import com.teachernavigator.teachernavigator.domain.models.Choice

/**
 * Created by root on 07.09.17
 */
data class PostNetwork(

        val id: Int? = null,
        val title: String? = null,
        val text: String? = null,
        val file: String? = null,
        val created: String? = null,
        val tags: List<String>? = null,
        val count_likes: Int? = null,
        val count_dislikes: Int? = null,
        val vote: Boolean? = null,
        val count_comments: Int? = null,
        val comments: List<CommentNetwork>? = null,
        val author: Author? = null,
        val choices: List<Choice>? = null

)