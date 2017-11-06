package com.teachernavigator.teachernavigator.domain.interactors

import com.teachernavigator.teachernavigator.data.models.FileInfo
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

    override fun respondVacancy(vacancyId: Int) =
            jobRepository.respondVacancy(vacancyId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())

    override fun removeResume(resumeId: Int) =
            jobRepository.removeResume(resumeId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())

    override fun prolongVacancy(vacancyId: Int) =
            jobRepository.prolongVacancy(vacancyId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())

    override fun prolongResume(resumeId: Int) =
            jobRepository.prolongResume(resumeId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())

    override fun removeVacancy(vacancyId: Int) =
            jobRepository.removeVacancy(vacancyId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())

    override fun loadVacancy(vacancyId: Int) =
            jobRepository.loadVacancy(vacancyId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())

    override fun loadResumeList(): Single<List<Resume>> =
            jobRepository.loadResumeList()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())

    override fun createResume(resumeRequest: ResumeRequest, fileInfo: FileInfo?): Single<Resume> =
            jobRepository.createResume(resumeRequest, fileInfo)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())

    override fun loadMyResume(): Single<List<Resume>> =
            jobRepository.loadMyResume()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())

    override fun loadMyVacancies(): Single<List<Vacancy>> =
            jobRepository.loadMyVacancies()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())

    override fun loadVacancies(): Single<List<Vacancy>> =
            jobRepository.loadVacancies()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())


    override fun createVacancy(vacancyRequest: VacancyRequest): Single<Vacancy> =
            jobRepository.createVacancy(vacancyRequest)

                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())

    override fun getTypesOfEmployment() =
            jobRepository.getTypesOfEmployment()

}