package com.teachernavigator.teachernavigator.domain.models

/**
 * Created by root on 07.09.17.
 */
class Comment(var id: Int?, var message: String?, var user: UserInComment?) {

    class UserInComment(var id: Int?, var full_name: String?, var avatars: Avatars?) {

        class Avatars(var id: Int?, var iavatard: String?)
    }
}