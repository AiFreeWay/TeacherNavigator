package com.teachernavigator.teachernavigator.data.network

import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.BuildConfig
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by root on 11.08.17.
 */
@Singleton
class NetworkController {

    init {
        if (BuildConfig.DEBUG) Logger.testLog("created CONTROLLER NetworkController")
    }
}
