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
    fun provideCreateJobsPresenter(presenter: CreateVacancyPresenter): ICreateVacancyPresenter = presenter

    @Provides
    @PerParentScreen
    fun provideMyJobsPresenter(presenter: MyVacanciesPresenter): IMyVacanciesPresenter = presenter

    @Provides
    @PerParentScreen
    fun provideMyResumePresenter(presenter: MyResumePresenter): IMyResumePresenter = presenter

    @Provides
    @PerParentScreen
    fun provideCreateResumePresenter(presenter: CreateResumePresenter): ICreateResumePresenter = presenter

    @Provides
    @PerParentScreen
    fun provideResumeListPresetner(presenter: ResumeListPresetner): IResumeListPresetner = presenter

    @Provides
    @PerParentScreen
    fun provideJobsPresenter(presenter: VacanciesPresenter): IVacanciesPresenter = presenter

}