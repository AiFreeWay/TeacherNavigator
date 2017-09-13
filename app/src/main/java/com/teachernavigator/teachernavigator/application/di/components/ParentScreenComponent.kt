package com.teachernavigator.teachernavigator.application.di.components

import com.example.root.androidtest.application.di.components.RootComponent
import com.teachernavigator.teachernavigator.application.di.modules.ParentScreenModule
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.data.repository.abstractions.IAuthRepository
import com.teachernavigator.teachernavigator.data.repository.abstractions.ITapeRepository
import com.teachernavigator.teachernavigator.domain.controllers.IPostController
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.AcAuthParentPresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.AcMainPresenter
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.FmtAuthPresenter
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.FmtRegistrationPresenter
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.FmtRestorePasswordPresenter
import com.teachernavigator.teachernavigator.presentation.screens.common.ParentView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.FmtMyCommentsPresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.FmtSavedPostsPresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.FmtTapePresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.AcPostCommentsPresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.AcPostDetailPresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.AcPostSearchPresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.FmtPostsListPresenter
import dagger.Component

/**
 * Created by root on 14.08.17.
 */
@PerParentScreen
@Component(modules = arrayOf(ParentScreenModule::class), dependencies = arrayOf(RootComponent::class))
interface ParentScreenComponent {

    fun provideTapeRepository() : ITapeRepository
    fun provideAuthRepository() : IAuthRepository
    fun providePostController() : IPostController
    fun provideParentView() : ParentView

    fun inject(presenter: AcMainPresenter)
    fun inject(presenter: FmtTapePresenter)
    fun inject(presenter: FmtMyCommentsPresenter)
    fun inject(presenter: FmtSavedPostsPresenter)

    fun inject(presenter: AcPostSearchPresenter)
    fun inject(presenter: AcPostDetailPresenter)
    fun inject(presenter: AcPostCommentsPresenter)
    fun inject(presenter: FmtPostsListPresenter)

    fun inject(presenter: AcAuthParentPresenter)
    fun inject(presenter: FmtAuthPresenter)
    fun inject(presenter: FmtRegistrationPresenter)
    fun inject(presenter: FmtRestorePasswordPresenter)
}