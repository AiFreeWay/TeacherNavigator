package com.example.root.androidtest.application.di.modules

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.teachernavigator.teachernavigator.data.network.adapters.ChatEnvelopeDeserializer
import com.teachernavigator.teachernavigator.data.network.adapters.UserDeserializer
import com.teachernavigator.teachernavigator.data.repository.MainRepository
import com.teachernavigator.teachernavigator.data.repository.abstractions.IMainRepository
import com.teachernavigator.teachernavigator.domain.models.Author
import com.teachernavigator.teachernavigator.domain.models.ChatEnvelope
import com.teachernavigator.teachernavigator.presentation.utils.DEFAULT_DATE_FORMAT
import dagger.Module
import dagger.Provides
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
                .setDateFormat(DEFAULT_DATE_FORMAT)
                .create()

        return GsonBuilder()
                .setLenient()
                .setDateFormat(DEFAULT_DATE_FORMAT)
                .registerTypeAdapter(Author::class.java, UserDeserializer(pureGson))
                .registerTypeAdapter(ChatEnvelope::class.java, ChatEnvelopeDeserializer)
                .create()
    }

    @Provides
    @Singleton
    fun provideMainRepository(repository: MainRepository): IMainRepository = repository
}