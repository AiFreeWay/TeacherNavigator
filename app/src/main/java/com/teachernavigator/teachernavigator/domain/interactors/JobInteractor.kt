package com.teachernavigator.teachernavigator.domain.interactors

import com.teachernavigator.teachernavigator.data.network.requests.ResumeRequest
import com.teachernavigator.teachernavigator.data.network.requests.VacancyRequest
import com.teachernavigator.teachernavigator.data.repository.abstractions.IJobRepository
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IJobInteractor
import com.teachernavigator.teachernavigator.domain.models.Resume
import com.teachernavigator.teachernavigator.domain.models.Vacancy
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by root on 22.09.17
 */
class JobInteractor @Inject constructor(private val jobRepository: IJobRepository) : IJobInteractor {

    override fun loadResumeList(): Single<List<Resume>> =
            jobRepository.loadResumeList()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())

    override fun createResume(resumeRequest: ResumeRequest): Single<Resume> =
        jobRepository.createResume(resumeRequest)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())

    override fun loadMyResume():  Single<List<Resume>> =
            jobRepository.loadMyResume()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())

    override fun loadMyVacancies(): Single<List<Vacancy>> =
            jobRepository.loadMyVacancies()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())


    override fun createVacancy(vacancyRequest: VacancyRequest): Single<Vacancy> =
            jobRepository.createVacancy(vacancyRequest)

                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())

    override fun getTypesOfEmployment() =
            jobRepository.getTypesOfEmployment()

}