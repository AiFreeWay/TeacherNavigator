package com.teachernavigator.teachernavigator.domain.interactors

import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.data.repository.abstractions.IProfileRepository
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IProfileInteractor
import com.teachernavigator.teachernavigator.domain.mappers.ProfileMapper
import com.teachernavigator.teachernavigator.domain.models.Profile
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by root on 18.09.17.
 */
class ProfileInteractor @Inject constructor(private val mRepository: IProfileRepository) : IProfileInteractor {

    init {
        Logger.logDebug("created INTERACTOR ProfileInteractor")
    }

    override fun getProfile(): Observable<Profile> =
            mRepository.getProfile()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .map { ProfileMapper.mapProfile(it) }
}