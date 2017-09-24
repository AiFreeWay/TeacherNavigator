package com.teachernavigator.teachernavigator.data.repository.abstractions

import com.teachernavigator.teachernavigator.data.network.requests.VacancyRequest
import com.teachernavigator.teachernavigator.domain.models.Resume
import com.teachernavigator.teachernavigator.domain.models.TypeOfEmployment
import com.teachernavigator.teachernavigator.domain.models.Vacancy
import io.reactivex.Single

/**
 * Created by lliepmah on 23.09.17
 */
interface IJobRepository {

    fun createVacancy(vacancyRequest: VacancyRequest): Single<Vacancy>
    fun loadMyVacancies(): Single<List<Vacancy>>
    fun loadMyResume(): Single<List<Resume>>

    fun getTypesOfEmployment(): List<TypeOfEmployment>

    /* Default methods */
    fun getTypeOfEmployment(id: Int): TypeOfEmployment {
        val typesOfEmployment = getTypesOfEmployment()


        return typesOfEmployment
                .find { it.id == id }
                ?: TypeOfEmployment.dummy
    }


}