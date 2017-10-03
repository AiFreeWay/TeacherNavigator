package com.teachernavigator.teachernavigator.data.network.requests

import com.google.gson.annotations.SerializedName

/**
 * Created by lliepmah on 25.09.17
 */
data class ResumeRequest(
        @SerializedName("career_objective") val careerObjective: String,
        @SerializedName("district_council") val districtCouncil: Int,
        @SerializedName("amount_of_wages") val salary: String,
        val education: String,
        val experience: String,
        val resumePath: String? = null,
        val mime: String? = null
)