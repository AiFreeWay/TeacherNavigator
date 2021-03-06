package com.teachernavigator.teachernavigator.domain.models

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by lliepmah on 24.09.17
 */
data class Resume(

        val id: Int,
        @SerializedName("days_until_expired") val daysRemains: Int,
        @SerializedName("career_objective") val careerObjective: String,
        @SerializedName("district_council") val districtCouncil: Int,
        @SerializedName("amount_of_wages") val salary: String,
        val education: String,
        val experience: String,
        val created: Date,
        val expired: Date,
        val file: String?,
        val user: Profile? = null,
        val isMine: Boolean = false,
        val appropriate : List<Vacancy>? = null

)


