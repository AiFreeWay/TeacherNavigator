package com.teachernavigator.teachernavigator.domain.models

/**
 * Created by root on 18.09.17
 */
class Profile {

    val id: Int? = null
    val full_name: String? = null
    val avatar: String? = null
    val raiting: Array<Rating>? = null
    val count_publications: Int? = null
    val count_subscribers: Int? = null
    val count_comments: Int? = null

    class Rating {
        val count_likes: Int? = null
        val count_dislikes: Int? = null
    }
}