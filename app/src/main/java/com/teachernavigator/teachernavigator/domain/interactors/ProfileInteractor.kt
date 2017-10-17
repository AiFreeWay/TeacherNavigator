package com.teachernavigator.teachernavigator.domain.interactors

import com.teachernavigator.teachernavigator.data.models.FileInfo
import com.teachernavigator.teachernavigator.data.repository.abstractions.IProfileRepository
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IProfileInteractor
import com.teachernavigator.teachernavigator.domain.models.About
import com.teachernavigator.teachernavigator.domain.models.Profile
import com.teachernavigator.teachernavigator.presentation.models.Specialist
import com.teachernavigator.teachernavigator.presentation.utils.applySchedulers
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by root on 18.09.17
 */
class ProfileInteractor @Inject constructor(private val mRepository: IProfileRepository) : IProfileInteractor {

    override fun askSpecialist(specialist: Specialist, question: String): Single<Unit> =
            mRepository.askSpecialist(specialist, question)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())

    override fun loadAbout(): Single<List<About>> =
            mRepository.loadAbout()
                    .applySchedulers()

    override fun getProfile(): Single<Profile> =
            mRepository.getProfile()
                    .map { it }
                    .applySchedulers()

    override fun getProfile(userId: Int): Single<Profile> =
            mRepository.getProfile(userId)
                    .map { it }
                    .applySchedulers()

    override fun exit() {
        mRepository.exit()
    }

    override fun uploadAvatar(fileInfo: FileInfo): Single<Profile> =
            mRepository.uploadAvatar(fileInfo)
                    .flatMap { getProfile() }
                    .applySchedulers()


}
