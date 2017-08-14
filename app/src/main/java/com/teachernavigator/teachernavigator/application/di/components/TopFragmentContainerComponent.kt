package com.teachernavigator.teachernavigator.application.di.components

import com.example.root.androidtest.application.di.components.RootComponent
import com.teachernavigator.teachernavigator.application.di.modules.TopFragmentContainerModule
import com.teachernavigator.teachernavigator.application.di.scopes.PerTopFragmentContainer
import com.teachernavigator.teachernavigator.presentation.ui.main.presenters.AcMainPresenter
import dagger.Component

/**
 * Created by root on 14.08.17.
 */
@PerTopFragmentContainer
@Component(modules = arrayOf(TopFragmentContainerModule::class), dependencies = arrayOf(RootComponent::class))
interface TopFragmentContainerComponent {

    fun inject(presenter: AcMainPresenter)
}