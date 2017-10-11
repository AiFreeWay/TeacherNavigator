package com.teachernavigator.teachernavigator.domain.models

import java.io.Serializable

/**
 * Created by root on 22.08.17.
 */
data class Post(var title: String? = null,
                var text: String? = null) : Serializable {

    var id: Int? = null


    var file: String? = null
    var created: String? = null
    var tags: List<String>? = null
    var countLikes: Int? = null
    var countDislikes: Int? = null
    var vote: Boolean? = null
    var countComments: Int? = null
    var comments: List<Comment>? = null
    var author: Author? = null
    var choices: List<Choice>? = null
}