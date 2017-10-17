package com.teachernavigator.teachernavigator.presentation.transformers

import android.content.Context
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.domain.models.Resume
import com.teachernavigator.teachernavigator.presentation.models.ResumeModel
import javax.inject.Inject

/**
 * Created by lliepmah on 24.09.17
 */
@PerParentScreen
class ResumeTransformer
@Inject
constructor(private val context: Context,
            private var vacancyTransformer: VacancyTransformer) : EntityTransformer<Resume, ResumeModel> {

    override fun transform(from: Resume): ResumeModel =
            ResumeModel(
                    id = from.id,
                    daysRemains = context.spanned(R.string.remains_days_format, from.daysRemains),
                    careerObjective = from.careerObjective,
                    districtCouncil = context.spanned(R.string.district_council_number, from.districtCouncil),
                    education = from.education,
                    experience = from.experience,

                    salary = obtainSalary(from.salary),

                    created = from.created,
                    expired = from.expired,

                    file = from.file,

                    userName = from.user?.full_name ?: context.getString(R.string.unknown),
                    userAvatar = from.user?.avatar ?: "",
                    isMine = from.isMine,
                    appropriate = from.appropriate?.map(vacancyTransformer::transform) ?: emptyList(),
                    appropriateCount = context.spanned(R.string.appropriate_vacancies_count, from.appropriate?.size ?: 0)

            )

    private fun obtainSalary(salary: String): CharSequence {
        val salaryNumber = salary.toIntOrNull()

        return if (salaryNumber != null) {
            if (salaryNumber > 0) {
                context.formatRoubles(salaryNumber)
            } else {
                ""
            }
        } else {
            salary
        }
    }

}