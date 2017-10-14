package com.teachernavigator.teachernavigator.application.di.components

import com.example.root.androidtest.application.di.components.RootComponent
import com.teachernavigator.teachernavigator.application.di.modules.ParentScreenModule
import com.teachernavigator.teachernavigator.application.di.modules.PresentersModule
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.data.repository.abstractions.IAuthRepository
import com.teachernavigator.teachernavigator.data.repository.abstractions.IProfileRepository
import com.teachernavigator.teachernavigator.data.repository.abstractions.ISettingsRepository
import com.teachernavigator.teachernavigator.data.repository.abstractions.ITapeRepository
import com.teachernavigator.teachernavigator.domain.controllers.IPostController
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.AuthFragment
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.RegistrationFragment
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.RestorePasswordFragment
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.AcAuthParentPresenter
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.AuthPresenter
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.RegistrationPresenter
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.RestorePasswordPresenter
import com.teachernavigator.teachernavigator.presentation.screens.common.ParentView
import com.teachernavigator.teachernavigator.presentation.screens.info.fragments.*
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.*
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.AddPublicationFragment
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.PostCommentsFragment
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.SavedPostsFragment
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.TapeFragment
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.*
import com.teachernavigator.teachernavigator.presentation.screens.settings.presenters.FmtAppSettingsPresenter
import com.teachernavigator.teachernavigator.presentation.screens.settings.presenters.FmtProfileSettingsPresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.fragments.PostsSearchFragment
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.AcPostCommentsPresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.AcPostDetailPresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.FmtPostsListPresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.PostSearchPresenter
import dagger.Component

/**
 * Created by root on 14.08.17.
 */
@PerParentScreen
@Component(modules = arrayOf(ParentScreenModule::class, PresentersModule::class), dependencies = arrayOf(RootComponent::class))
interface ParentScreenComponent {

    fun provideTapeRepository(): ITapeRepository
    fun provideAuthRepository(): IAuthRepository
    fun provideSettingsRepository(): ISettingsRepository
    fun provideProfileRepository(): IProfileRepository
    fun providePostController(): IPostController
    fun provideParentView(): ParentView

    //Main presenters
    fun inject(presenter: AcMainPresenter)

    fun inject(presenter: AcProfilePresenter)
    fun inject(presenter: TapePresenter)
    fun inject(presenter: FmtMyCommentsPresenter)
    fun inject(presenter: SavedPostsPresenter)
    fun inject(presenter: FmtMyPublicationsPresenter)
    fun inject(presenter: FmtSettingsPresenter)
    fun inject(presenter: AddPublicationPresenter)

    //Tape presenters
    fun inject(presenter: PostSearchPresenter)

    fun inject(presenter: AcPostDetailPresenter)
    fun inject(presenter: AcPostCommentsPresenter)
    fun inject(presenter: FmtPostsListPresenter)

    //Auth presenters
    fun inject(presenter: AcAuthParentPresenter)

    fun inject(presenter: AuthPresenter)
    fun inject(presenter: RegistrationPresenter)
    fun inject(presenter: RestorePasswordPresenter)

    //Settings presenters
    fun inject(presenter: FmtAppSettingsPresenter)

    fun inject(presenter: FmtProfileSettingsPresenter)

    //Job fragments
    fun inject(jobsBankFragment: JobsBankFragment)

    fun inject(createVacancyFragment: CreateVacancyFragment)
    fun inject(myVacanciesFragment: MyVacanciesFragment)
    fun inject(myResumeFragment: MyResumeFragment)
    fun inject(createResumeFragment: CreateResumeFragment)
    fun inject(resumeListFragment: ResumeListFragment)
    fun inject(jobsFragment: VacanciesFragment)
    fun inject(vacancyFragment: VacancyFragment)

    //Information presenters
    fun inject(importantToKnowFragment: ImportantToKnowFragment)

    fun inject(aboutFragment: AboutFragment)
    fun inject(supportFragment: SupportFragment)
    fun inject(askSpecialistFragment: AskSpecialistFragment)
    fun inject(postCommentsFragment: PostCommentsFragment)
    fun inject(tagsFragment: TagsFragment)
    fun inject(addPublicationFragment: AddPublicationFragment)
    fun inject(registrationFragment: RegistrationFragment)
    fun inject(authFragment: AuthFragment)
    fun inject(restorePasswordFragment: RestorePasswordFragment)
    fun inject(postsSearchFragment: PostsSearchFragment)
    fun inject(savedPostsFragment: SavedPostsFragment)
    fun inject(tapeFragment: TapeFragment)

}