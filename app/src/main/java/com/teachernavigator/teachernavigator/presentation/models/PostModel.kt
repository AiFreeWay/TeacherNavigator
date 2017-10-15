package com.teachernavigator.teachernavigator.presentation.models

import com.teachernavigator.teachernavigator.domain.models.PostType

/**
 * Created by lliepmah on 07.10.17
 */
data class PostModel(

        override val id: Int,
        val title: String,
        val text: String,
        val created: String,
        val tags: List<String>,
        var count_likes: Int,
        var count_dislikes: Int,
        var vote: Boolean? = null,
        val count_comments: Int,
        val comments: List<Any>,

        val authorId: Int,
        val authorName: String,
        val authorAvatar: String?,

        val type: PostType,
        val file: String? = null,

        val shortTitle: String,
        val choices: List<ChoiceModel>,
        val pollPassed: Boolean

) : Model(id)