package com.teachernavigator.teachernavigator.domain.interactors

import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.data.repository.abstractions.IProfileRepository
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IProfileInteractor
import com.teachernavigator.teachernavigator.domain.mappers.ProfileMapper
import com.teachernavigator.teachernavigator.domain.models.About
import com.teachernavigator.teachernavigator.domain.models.Profile
import com.teachernavigator.teachernavigator.presentation.models.Specialist
import com.teachernavigator.teachernavigator.presentation.utils.applySchedulers
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import javax.inject.Inject

/**
 * Created by root on 18.09.17.
 */
class ProfileInteractor @Inject constructor(private val mRepository: IProfileRepository) : IProfileInteractor {

    override fun askSpecialist(specialist: Specialist, question: String): Single<Unit> =
            mRepository.askSpecialist(specialist, question)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())

    override fun loadAbout(): Single<List<About>> =
            mRepository.loadAbout()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())

    override fun getProfile(): Single<Profile> =
            mRepository.getProfile()
                    .applySchedulers()
                    .map { it }

    override fun getProfile(userId: Int): Single<Profile> =
            mRepository.getProfile(userId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .map { it }

    override fun exit() {
        mRepository.exit()
    }

    override fun uploadPhoto(file: File): Single<File> =
            mRepository.uploadPhoto(file)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
}
