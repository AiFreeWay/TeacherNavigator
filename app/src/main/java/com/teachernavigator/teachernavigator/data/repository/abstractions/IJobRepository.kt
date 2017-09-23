package com.teachernavigator.teachernavigator.data.repository.abstractions

import com.teachernavigator.teachernavigator.data.network.requests.VacancyRequest
import com.teachernavigator.teachernavigator.domain.models.Monade
import com.teachernavigator.teachernavigator.domain.models.TypeOfEmployment
import com.teachernavigator.teachernavigator.domain.models.Vacancy
import io.reactivex.Single

/**
 * Created by lliepmah on 23.09.17
 */
interface IJobRepository {

    fun createVacancy(vacancyRequest: VacancyRequest): Single<Vacancy>
    fun getTypesOfEmployment(): List<TypeOfEmployment>

}