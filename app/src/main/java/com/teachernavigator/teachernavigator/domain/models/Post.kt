package com.teachernavigator.teachernavigator.domain.models

import java.io.Serializable

/**
 * Created by root on 22.08.17.
 */
class Post : Serializable {

    var id: Int? = null
    var title: String? = null
    var text: String? = null
    var file: String? = null
    var created: String? = null
    var tags: List<String>? = null
    var countLikes: Int? = null
    var countDislikes: Int? = null
    var vote: Boolean? = null
    var countComments: Int? = null
    var comments: List<Comment>? = null
}