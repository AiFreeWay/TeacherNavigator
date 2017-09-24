package com.teachernavigator.teachernavigator.presentation.transformers

import android.content.Context
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.data.repository.abstractions.IJobRepository
import com.teachernavigator.teachernavigator.domain.models.Vacancy
import com.teachernavigator.teachernavigator.presentation.models.VacancyModel
import javax.inject.Inject

/**
 * Created by lliepmah on 24.09.17
 */
@PerParentScreen
class VacancyTransformer
@Inject
constructor(private val context: Context,
            private val jobRepository: IJobRepository) : EntityTransformer<Vacancy, VacancyModel> {


    override fun transform(from: Vacancy): VacancyModel = from.let {

        val typeOfEmployment = jobRepository.getTypeOfEmployment(it.typeOfEmployment).name
        VacancyModel(
                id = it.id,
                organization = it.organization,
                vacancy = it.vacancy,
                salary = context.spanned(R.string.cost_roubles, it.salary),
                city = it.city,
                experience = it.experience,
                typeOfEmployment = context.spanned(R.string.type_of_employment_formatted, typeOfEmployment),
                responsibility = context.spanned(R.string.responsibilities_text, it.responsibility),
                typeOfInstitution = getTypeOfInstitution(from.typeOfInstitution),
                daysRemains = context.spanned(R.string.remains_days_format, it.daysRemains),

                created = it.created,
                expired = it.expired,
                user = it.user)

    }

    private fun getTypeOfInstitution(typeOfInstitution: Int) = when (typeOfInstitution) {
        Vacancy.INSTITUTION_SCHOOL -> context.getString(R.string.school)
        Vacancy.INSTITUTION_COLLEDGE -> context.getString(R.string.college)
        Vacancy.INSTITUTION_UNIVERSITY -> context.getString(R.string.university)
        else -> ""
    }

}