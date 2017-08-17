package com.teachernavigator.teachernavigator.data.repository

import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.data.network.NetworkController
import com.teachernavigator.teachernavigator.data.repository.abstractions.IAuthRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by root on 11.08.17.
 */
class AuthRepository @Inject constructor(private val mNetwokController: NetworkController) : IAuthRepository {

    init {
        if (BuildConfig.DEBUG) Logger.logDebug("created REPOSITORY AuthRepository")
    }

    override fun isAuth(): Observable<Boolean> = Observable.just(true)
}