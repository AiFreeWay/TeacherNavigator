package com.teachernavigator.teachernavigator.presentation.models

import com.teachernavigator.teachernavigator.domain.models.Author
import java.util.*

/**
 * Created by lliepmah on 24.09.17
 */
data class VacancyModel(
        override val id: Int,

        val organization: String,
        val vacancy: String,
        val salary: CharSequence,
        val city: String,
        val experience: String,
        val typeOfEmployment: CharSequence,
        val responsibility: CharSequence,
        val typeOfInstitution: TypeOfInstitution,
        val daysRemains: CharSequence,

        val responses: List<ResponseModel>,
        val created: Date,
        val expired: Date,
        val responded: Boolean = false,

        val user: Author) : Model(id)