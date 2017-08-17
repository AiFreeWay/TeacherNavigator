package com.example.root.androidtest.application.di.modules

import android.content.Context
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.data.network.NetworkController
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by root on 03.08.17.
 */
@Module
class RootModule(private val mContext: Context) {

    init {
        if (BuildConfig.DEBUG) Logger.logDebug("created MODULE RootModule")
    }

    @Provides
    fun  provideContext(): Context {
        return mContext
    }

    @Provides
    @Singleton
    fun  provideNetworkController() : NetworkController {
        return NetworkController()
    }
}