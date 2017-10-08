package com.teachernavigator.teachernavigator.data.models

import com.teachernavigator.teachernavigator.domain.models.Author
import java.util.*

/**
 * Created by root on 07.09.17.
 */
data class CommentNetwork(

        val id: Int? = null,
        val message: String? = null,
        val user: UserInComment? = null,
        val author: Author? = null,
        val created: Date? = null) {

    class UserInComment {
        var id: Int? = null
        var full_name: String? = null
        var avatars: Avatars? = null

        class Avatars {
            var id: Int? = null
            var avatar: String? = null
        }
    }

}


//val id: Int? = null,
//val message: String? = null,
//val user: Author? = null,
//val author: Author? = null,
//val created: Date? = null