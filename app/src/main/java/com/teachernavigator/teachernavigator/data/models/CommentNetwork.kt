package com.teachernavigator.teachernavigator.data.models

/**
 * Created by root on 07.09.17.
 */
class CommentNetwork {

    var id: Int? = null
    var message: String? = null
    var user: UserInComment? = null

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