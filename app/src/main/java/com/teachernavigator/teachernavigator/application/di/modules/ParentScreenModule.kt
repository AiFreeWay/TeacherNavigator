package com.teachernavigator.teachernavigator.application.di.modules

import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.presentation.ui.base.ParentView
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
        if (BuildConfig.DEBUG) Logger.testLog("created MODULE ParentScreenModule")

        mCicerone = Cicerone.create()
        mCicerone.navigatorHolder.setNavigator(FragmentNavigator(viewScreens.getActivity(),
                viewScreens.getSupportFragmentManager(),
                viewScreens.getFragmentLayoutId()))
    }

    @Provides
    fun provideFragmentRouter(): Router {
        return mCicerone.router
    }
}