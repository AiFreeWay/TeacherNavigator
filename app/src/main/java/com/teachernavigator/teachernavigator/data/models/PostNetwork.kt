package com.teachernavigator.teachernavigator.data.models

import com.teachernavigator.teachernavigator.domain.models.Author
import com.teachernavigator.teachernavigator.domain.models.Choice

/**
 * Created by root on 07.09.17.
 */
class PostNetwork {

    var id: Int? = null
    var title: String? = null
    var text: String? = null
    var file: String? = null
    var created: String? = null
    var tags: List<String>? = null
    var count_likes: Int? = null
    var count_dislikes: Int? = null
    var vote: Boolean? = null
    var count_comments: Int? = null
    var comments: List<CommentNetwork>? = null
    var author: Author? = null
    var choices: List<Choice>? = null
}