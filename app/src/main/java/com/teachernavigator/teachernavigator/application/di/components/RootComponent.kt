package com.example.root.androidtest.application.di.components

import android.content.Context
import com.example.root.androidtest.application.di.modules.RootModule
import com.google.gson.Gson
import com.teachernavigator.teachernavigator.data.network.NetworkController
import com.teachernavigator.teachernavigator.data.repository.abstractions.IMainRepository
import dagger.Component
import javax.inject.Singleton

/**
 * Created by root on 03.08.17
 */
@Singleton
@Component(modules = arrayOf(RootModule::class))
interface RootComponent {

    fun provideContext(): Context
    fun provideNetworkController(): NetworkController
    fun provideMainRepository(): IMainRepository
    fun provideGson(): Gson
}