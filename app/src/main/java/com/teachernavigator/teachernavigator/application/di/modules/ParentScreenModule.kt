package com.teachernavigator.teachernavigator.application.di.modules

import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.data.repository.abstractions.*
import com.teachernavigator.teachernavigator.domain.controllers.IPostController
import com.teachernavigator.teachernavigator.domain.controllers.PostController
import com.teachernavigator.teachernavigator.domain.interactors.*
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.*
import com.teachernavigator.teachernavigator.presentation.screens.common.ParentView
import com.teachernavigator.teachernavigator.presentation.utils.FragmentNavigator
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

/**
 * Created by root on 14.08.17
 */
@Module
class ParentScreenModule(private val mParentView: ParentView) {

    private val mCicerone: Cicerone<Router> = Cicerone.create()

    init {
        mCicerone.navigatorHolder.setNavigator(FragmentNavigator(mParentView.getActivity(),
                mParentView.getSupportFragmentManager(),
                mParentView.getFragmentLayoutId()))
    }

    @Provides
    @PerParentScreen
    fun provideFragmentRouter(): Router = mCicerone.router

    @Provides
    @PerParentScreen
    fun provideTapeRepository(repository: IMainRepository): IPostsRepository = repository

    @Provides
    @PerParentScreen
    fun provideSettingsRepository(repository: IMainRepository): ISettingsRepository = repository

    @Provides
    @PerParentScreen
    fun provideAuthRepository(repository: IMainRepository): IAuthRepository = repository

    @Provides
    @PerParentScreen
    fun provideJobRepository(repository: IMainRepository): IJobRepository = repository

    @Provides
    @PerParentScreen
    fun provideProfileRepository(repository: IMainRepository): IProfileRepository = repository

    @Provides
    @PerParentScreen
    fun provideAuthInteractor(interactor : AuthInteractor): IAuthInteractor = interactor

    @Provides
    @PerParentScreen
    fun providePostsInteractor(interactor : PostsInteractor): IPostsInteractor = interactor

    @Provides
    @PerParentScreen
    fun provideJobInteractor(interactor : JobInteractor): IJobInteractor = interactor

    @Provides
    @PerParentScreen
    fun provideCommentsInteractor(interactor : CommentsInteractor): ICommentsInteractor = interactor

    @Provides
    @PerParentScreen
    fun providePostController(controller : PostController): IPostController = controller

    @Provides
    @PerParentScreen
    fun provideParentView(): ParentView = mParentView

    @Provides
    @PerParentScreen
    fun provideSettingsInteractor(interactor : SettingsInteractor): ISettingsInteractor = interactor

    @Provides
    @PerParentScreen
    fun provideProfileInteractor(interactor : ProfileInteractor): IProfileInteractor = interactor
}