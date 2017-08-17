package com.teachernavigator.teachernavigator.application.di.modules

import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.data.repository.AuthRepository
import com.teachernavigator.teachernavigator.data.repository.abstractions.IAuthRepository
import com.teachernavigator.teachernavigator.domain.interactors.AuthInteractor
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IAuthInteractor
import com.teachernavigator.teachernavigator.presentation.screens.base.ParentView
import com.teachernavigator.teachernavigator.presentation.utils.FragmentNavigator
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

/**
 * Created by root on 14.08.17.
 */
@Module
class ParentScreenModule(viewScreens: ParentView) {

    private val mCicerone: Cicerone<Router>

    init {
        if (BuildConfig.DEBUG) Logger.logDebug("created MODULE ParentScreenModule")

        mCicerone = Cicerone.create()
        mCicerone.navigatorHolder.setNavigator(FragmentNavigator(viewScreens.getActivity(),
                viewScreens.getSupportFragmentManager(),
                viewScreens.getFragmentLayoutId()))
    }

    @Provides
    fun provideFragmentRouter(): Router {
        return mCicerone.router
    }

    @Provides
    @PerParentScreen
    fun provideAuthInteractor(interactor : AuthInteractor): IAuthInteractor {
        return interactor
    }

    @Provides
    @PerParentScreen
    fun provideAuthRepository(repository : AuthRepository): IAuthRepository {
        return repository
    }
}