package com.teachernavigator.teachernavigator.presentation.models

import java.util.*

/**
 * Created by lliepmah on 24.09.17
 */
data class ResumeModel(

        override val id: Int,
        val daysRemains: CharSequence,
        val careerObjective: String,
        val districtCouncil: CharSequence,
        val education: String,
        val experience: CharSequence,
        val salary: CharSequence,

        val created: Date,
        val expired: Date,

        val file: Any?,

        val userName: String,
        val userAvatar: String,

        val isMine : Boolean

) : Model(id)