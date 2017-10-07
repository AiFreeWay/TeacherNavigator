package com.teachernavigator.teachernavigator.domain.models

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by lliepmah on 24.09.17
 */
data class Vacancy(
        val id: Int,
        @SerializedName("name_of_the_organization") val organization: String,
        val vacancy: String,
        @SerializedName("amount_of_wages") val salary: Int,
        val city: String,
        val experience: String,
        @SerializedName("type_of_employment") val typeOfEmployment: Int,
        val responsibility: String,
        @SerializedName("type_of_scool") val typeOfInstitution: Int,
        @SerializedName("days_until_expired") val daysRemains: Int,
        val created: Date,
        val expired: Date,
        val user: Author,
        val responded: Boolean = false,
        val response_vacancy: List<Response>? = null
) {

    companion object {
        val INSTITUTION_SCHOOL = 0
        val INSTITUTION_COLLEDGE = 1
        val INSTITUTION_UNIVERSITY = 2
    }
}