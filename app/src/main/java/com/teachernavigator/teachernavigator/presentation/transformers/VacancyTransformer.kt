package com.teachernavigator.teachernavigator.presentation.transformers

import android.content.Context
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.data.repository.abstractions.IJobRepository
import com.teachernavigator.teachernavigator.domain.models.Vacancy
import com.teachernavigator.teachernavigator.presentation.models.TypeOfInstitution
import com.teachernavigator.teachernavigator.presentation.models.VacancyModel
import javax.inject.Inject

/**
 * Created by lliepmah on 24.09.17
 */
@PerParentScreen
class VacancyTransformer
@Inject
constructor(private val context: Context,
            private val jobRepository: IJobRepository,
            private val responseTransformer: ResponseTransformer) : EntityTransformer<Vacancy, VacancyModel> {

    override fun transform(from: Vacancy): VacancyModel = from.let {

        val typeOfEmployment = jobRepository.getTypeOfEmployment(it.typeOfEmployment).name

        VacancyModel(
                id = it.id,
                organization = it.organization,
                vacancy = it.vacancy,
                salary = context.formatRoubles(it.salary),
                city = it.city,
                experience = it.experience,
                typeOfEmployment = context.spanned(R.string.type_of_employment_formatted, typeOfEmployment),
                responsibility = context.spanned(R.string.responsibilities_text, it.responsibility),
                typeOfInstitution = getTypeOfInstitution(from.typeOfInstitution),
                daysRemains = context.spanned(R.string.remains_days_format, it.daysRemains),
                responses = from.response_vacancy?.map(responseTransformer::transform) ?: listOf(),
                created = it.created,
                expired = it.expired,
                responded = it.responded,
                user = it.user)

    }

    private fun getTypeOfInstitution(typeOfInstitution: Int) = when (typeOfInstitution) {
        Vacancy.INSTITUTION_SCHOOL -> TypeOfInstitution.SCHOOL
        Vacancy.INSTITUTION_COLLEDGE -> TypeOfInstitution.COLLEGE
        Vacancy.INSTITUTION_UNIVERSITY -> TypeOfInstitution.UNIVERSITY
        else -> TypeOfInstitution.UNIVERSITY
    }

}