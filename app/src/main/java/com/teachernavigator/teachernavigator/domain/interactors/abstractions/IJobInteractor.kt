package com.teachernavigator.teachernavigator.domain.interactors.abstractions

import com.teachernavigator.teachernavigator.data.models.FileInfo
import com.teachernavigator.teachernavigator.data.network.requests.ResumeRequest
import com.teachernavigator.teachernavigator.data.network.requests.VacancyRequest
import com.teachernavigator.teachernavigator.domain.models.Resume
import com.teachernavigator.teachernavigator.domain.models.TypeOfEmployment
import com.teachernavigator.teachernavigator.domain.models.Vacancy
import io.reactivex.Single

/**
 * Created by lliepmah on 22.09.17
 */
interface IJobInteractor {

    fun createVacancy(vacancyRequest: VacancyRequest): Single<Vacancy>
    fun removeVacancy(vacancyId: Int): Single<Unit>
    fun removeResume(resumeId: Int): Single<Unit>
    fun prolongVacancy(vacancyId: Int): Single<Unit>
    fun respondVacancy(vacancyId: Int): Single<Unit>
    fun prolongResume(resumeId: Int): Single<Unit>
    fun createResume(resumeRequest: ResumeRequest, fileInfo: FileInfo?): Single<Resume>
    fun loadMyVacancies(): Single<List<Vacancy>>
    fun loadVacancies(): Single<List<Vacancy>>
    fun loadMyResume(): Single<List<Resume>>
    fun loadResumeList(): Single<List<Resume>>
    fun loadVacancy(vacancyId: Int): Single<Vacancy>

    fun getTypesOfEmployment(): List<TypeOfEmployment>

}