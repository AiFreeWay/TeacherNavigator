package com.teachernavigator.teachernavigator.domain.interactors

import com.teachernavigator.teachernavigator.data.network.requests.VacancyRequest
import com.teachernavigator.teachernavigator.data.repository.abstractions.IJobRepository
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IJobInteractor
import com.teachernavigator.teachernavigator.domain.models.Monade
import com.teachernavigator.teachernavigator.domain.models.Vacancy
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by root on 22.09.17
 */
class JobInteractor @Inject constructor(private val jobRepository: IJobRepository) : IJobInteractor {


    override fun createVacancy(vacancyRequest: VacancyRequest): Single<Vacancy> =
            jobRepository.createVacancy(vacancyRequest)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())

    override fun getTypesOfEmployment() =
            jobRepository.getTypesOfEmployment()

}