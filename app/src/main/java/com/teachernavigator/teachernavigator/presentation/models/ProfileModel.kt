package com.teachernavigator.teachernavigator.presentation.models

/**
 * Created by Arthur Korchagin on 17.10.17
 */

data class ProfileModel(

        override val id: Int,
        val name: String,
        val avatarUrl: String,
        val countLikes: Int,
        val countDislikes: Int,
        val countPublications: Int,
        val countSubscribers: Int,
        val countComments: Int,
        val ratingString: String,
        val isMine: Boolean

) : Model(id)