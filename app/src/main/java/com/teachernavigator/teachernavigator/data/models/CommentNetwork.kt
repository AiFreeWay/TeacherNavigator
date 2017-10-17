package com.teachernavigator.teachernavigator.data.models

import com.teachernavigator.teachernavigator.domain.models.Author
import java.util.*

/**
 * Created by root on 07.09.17
 */
open class CommentNetwork(

        open val id: Int? = null,
        open val message: String? = null,
        open val user: UserInComment? = null,
        open var author: Author? = null,
        open val created: Date? = null) {

    class UserInComment {
        var id: Int? = null
        var full_name: String? = null
        var avatar: String? = null
    }

}