package com.teachernavigator.teachernavigator.data.network.requests

import com.google.gson.annotations.SerializedName

/**
 * Created by lliepmah on 25.09.17
 */
data class ResumeRequest(
        @SerializedName("career_objective") val careerObjective: String,
        @SerializedName("district_council") val districtCouncil: Int,
        val education: String,
        val experience: String)