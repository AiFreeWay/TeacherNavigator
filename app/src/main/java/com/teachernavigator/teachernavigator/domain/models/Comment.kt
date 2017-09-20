package com.teachernavigator.teachernavigator.domain.models

import java.io.Serializable

/**
 * Created by root on 07.09.17.
 */
class Comment(var id: Int?, var message: String?, var user: UserInComment?, var author: Author?) : Serializable {

    class UserInComment(var id: Int?, var full_name: String?, var avatars: Avatars?) : Serializable {

        class Avatars(var id: Int?, var avatar: String?) : Serializable
    }
}