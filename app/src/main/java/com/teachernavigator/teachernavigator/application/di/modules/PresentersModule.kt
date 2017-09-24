package com.teachernavigator.teachernavigator.application.di.modules

import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters.*
import com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters.abstractions.*
import dagger.Module
import dagger.Provides

/**
 *  @author Arthur Korchagin on 20.09.17.
 */
@Module
class PresentersModule {

    @Provides
    @PerParentScreen
    fun provideJobsBankPresenter(presenter: JobsBankPresenter): IJobsBankPresenter = presenter


    @Provides
    @PerParentScreen
    fun provideCreateJobsPresenter(presenter: CreateJobPresenter): ICreateJobPresenter = presenter

    @Provides
    @PerParentScreen
    fun provideMyJobsPresenter(presenter: MyJobsPresenter): IMyJobsPresenter = presenter

    @Provides
    @PerParentScreen
    fun provideMyResumePresenter(presenter: MyResumePresenter): IMyResumePresenter = presenter

    @Provides
    @PerParentScreen
    fun provideCreateResumePresenter(presenter: CreateResumePresenter): ICreateResumePresenter = presenter

}