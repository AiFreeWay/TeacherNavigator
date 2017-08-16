package com.teachernavigator.teachernavigator.data.repository

import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.data.network.NetworkController
import com.teachernavigator.teachernavigator.data.repository.abstractions.MainRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by root on 11.08.17.
 */
class MainRepositoryImpl @Inject constructor(private val mNetwokController: NetworkController) : MainRepository {

    init {
        if (BuildConfig.DEBUG) Logger.logDebug("created REPOSITORY MainRepositoryImpl")
    }
}