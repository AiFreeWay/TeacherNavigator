package com.example.root.androidtest.application.di.modules

import android.content.Context
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.data.network.NetworkController
import com.teachernavigator.teachernavigator.data.repository.MainRepository
import com.teachernavigator.teachernavigator.data.repository.abstractions.IMainRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by root on 03.08.17.
 */
@Module
class RootModule(private val mContext: Context) {

    init {
        Logger.logDebug("created MODULE RootModule")
    }

    @Provides
    fun  provideContext(): Context = mContext

    @Provides
    @Singleton
    fun  provideNetworkController() : NetworkController = NetworkController()

    @Provides
    @Singleton
    fun provideMainRepository(repository : MainRepository): IMainRepository = repository
}