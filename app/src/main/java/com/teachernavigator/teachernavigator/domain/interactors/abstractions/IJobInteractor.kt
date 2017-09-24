package com.teachernavigator.teachernavigator.domain.interactors.abstractions

import com.teachernavigator.teachernavigator.data.network.requests.VacancyRequest
import com.teachernavigator.teachernavigator.domain.models.TypeOfEmployment
import com.teachernavigator.teachernavigator.domain.models.Vacancy
import io.reactivex.Single

/**
 * Created by lliepmah on 22.09.17
 */
interface IJobInteractor {

    fun createVacancy(vacancyRequest: VacancyRequest): Single<Vacancy>
    fun loadMyVacancies(): Single<List<Vacancy>>

    fun getTypesOfEmployment(): List<TypeOfEmployment>

}