package com.example.root.androidtest.application.di.modules

import android.content.Context
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.data.network.NetworkController
import com.teachernavigator.teachernavigator.data.repository.abstractions.MainRepository
import com.teachernavigator.teachernavigator.data.repository.MainRepositoryImpl
import dagger.Module
import dagger.Provides

/**
 * Created by root on 03.08.17.
 */
@Module
class RootModule(private val mContext: Context) {

    init {
        if (BuildConfig.DEBUG) Logger.testLog("created MODULE RootModule")
    }

    @Provides
    fun  provideContext(): Context {
        return mContext
    }
    @Provides
    fun  provideNetworkController() : NetworkController {
        return NetworkController()
    }

    @Provides
    fun provideRepository(repository : MainRepositoryImpl): MainRepository {
        return repository
    }
}