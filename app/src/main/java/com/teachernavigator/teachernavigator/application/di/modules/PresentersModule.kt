package com.teachernavigator.teachernavigator.application.di.modules

import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters.JobsBankPresenter
import com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters.abstractions.IJobsBankPresenter
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

}