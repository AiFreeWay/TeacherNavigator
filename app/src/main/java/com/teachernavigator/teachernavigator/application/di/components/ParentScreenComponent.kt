package com.teachernavigator.teachernavigator.application.di.components

import com.example.root.androidtest.application.di.components.RootComponent
import com.teachernavigator.teachernavigator.application.di.modules.ParentScreenModule
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.data.repository.abstractions.IAuthRepository
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.AcMainPresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.FmtTapePresenter
import dagger.Component

/**
 * Created by root on 14.08.17.
 */
@PerParentScreen
@Component(modules = arrayOf(ParentScreenModule::class), dependencies = arrayOf(RootComponent::class))
interface ParentScreenComponent {

    fun inject(presenter: AcMainPresenter)
    fun inject(presenter: FmtTapePresenter)
}