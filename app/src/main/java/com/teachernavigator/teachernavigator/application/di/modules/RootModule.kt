package com.example.root.androidtest.application.di.modules

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.teachernavigator.teachernavigator.data.network.adapters.ChatEnvelopeDeserializer
import com.teachernavigator.teachernavigator.data.network.adapters.DateTypeAdapter
import com.teachernavigator.teachernavigator.data.network.adapters.UserDeserializer
import com.teachernavigator.teachernavigator.data.repository.MainRepository
import com.teachernavigator.teachernavigator.data.repository.abstractions.IMainRepository
import com.teachernavigator.teachernavigator.domain.models.Author
import com.teachernavigator.teachernavigator.domain.models.ChatEnvelope
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Singleton

/**
 * Created by root on 03.08.17
 */
@Module
class RootModule(private val mContext: Context) {

    @Provides
    fun provideContext(): Context = mContext

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val pureGson = GsonBuilder()
                .setLenient()
                .registerTypeAdapter(Date::class.java, DateTypeAdapter)
                .create()

        return GsonBuilder()
                .setLenient()
                .registerTypeAdapter(Author::class.java, UserDeserializer(pureGson))
                .registerTypeAdapter(Date::class.java, DateTypeAdapter)
                .registerTypeAdapter(ChatEnvelope::class.java, ChatEnvelopeDeserializer)
                .create()
    }

    @Provides
    @Singleton
    fun provideMainRepository(repository: MainRepository): IMainRepository = repository
}