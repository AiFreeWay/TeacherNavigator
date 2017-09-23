package com.teachernavigator.teachernavigator.domain.models

import com.google.gson.annotations.SerializedName

/**
 * Created by lliepmah on 24.09.17
 */
data class Vacancy(
        @SerializedName("name_of_the_organization") val rganization: String,
        val vacancy: String,
        @SerializedName("amount_of_wages") val salary: Int,
        val city: String,
        val experience: String,
        @SerializedName("type_of_employment") val typeOfEmployment: Int,
        val responsibility: String,
        @SerializedName("type_of_scool") val typeOfInstitution: Int)