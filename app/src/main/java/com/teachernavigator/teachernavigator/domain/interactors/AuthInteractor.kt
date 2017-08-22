package com.teachernavigator.teachernavigator.domain.interactors

import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.data.repository.abstractions.IAuthRepository
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IAuthInteractor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by root on 17.08.17.
 */
class AuthInteractor @Inject constructor(private val mRepository: IAuthRepository) : IAuthInteractor {

    init {
        if (BuildConfig.DEBUG) Logger.logDebug("created INTERACTOR AuthInteractor")
    }

    override fun isAuth(): Observable<Boolean> =
            mRepository.isAuth()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
}