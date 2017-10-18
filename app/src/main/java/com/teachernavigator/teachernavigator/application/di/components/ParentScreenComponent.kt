package com.teachernavigator.teachernavigator.application.di.components

import com.example.root.androidtest.application.di.components.RootComponent
import com.teachernavigator.teachernavigator.application.di.modules.ParentScreenModule
import com.teachernavigator.teachernavigator.application.di.modules.PresentersModule
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.data.repository.abstractions.IAuthRepository
import com.teachernavigator.teachernavigator.data.repository.abstractions.IPostsRepository
import com.teachernavigator.teachernavigator.data.repository.abstractions.IProfileRepository
import com.teachernavigator.teachernavigator.data.repository.abstractions.ISettingsRepository
import com.teachernavigator.teachernavigator.presentation.screens.auth.activities.AuthActivity
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.AuthFragment
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.RegistrationFragment
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.RestorePasswordFragment
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.AuthParentPresenter
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.AuthPresenter
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.RegistrationPresenter
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.RestorePasswordPresenter
import com.teachernavigator.teachernavigator.presentation.screens.common.ParentView
import com.teachernavigator.teachernavigator.presentation.screens.info.fragments.*
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.*
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.*
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.*
import com.teachernavigator.teachernavigator.presentation.screens.settings.fragments.AppSettingsFragment
import com.teachernavigator.teachernavigator.presentation.screens.settings.presenters.AppSettingsPresenter
import com.teachernavigator.teachernavigator.presentation.screens.settings.presenters.ProfileSettingsPresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.fragments.PostsListFragment
import com.teachernavigator.teachernavigator.presentation.screens.tape.fragments.PostsSearchFragment
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.PostSearchPresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.PostsListPresenter
import dagger.Component

/**
 * Created by root on 14.08.17
 */
@PerParentScreen
@Component(modules = arrayOf(ParentScreenModule::class, PresentersModule::class), dependencies = arrayOf(RootComponent::class))
interface ParentScreenComponent {

    fun provideTapeRepository(): IPostsRepository
    fun provideAuthRepository(): IAuthRepository
    fun provideSettingsRepository(): ISettingsRepository
    fun provideProfileRepository(): IProfileRepository
    fun provideParentView(): ParentView

    //Main presenters
    fun inject(presenter: AcMainPresenter)

    fun inject(presenter: ProfilePresenter)
    fun inject(presenter: TapePresenter)
    fun inject(presenter: MyCommentsPresenter)
//    fun inject(presenterApp: MyPublicationsPresenter)
    fun inject(presenterApp: SettingsPresenter)
    fun inject(presenter: AddPublicationPresenter)

    //Tape presenters
    fun inject(presenter: PostSearchPresenter)

    fun inject(presenter: PostsListPresenter)

    //Auth presenters
    fun inject(presenter: AuthParentPresenter)

    fun inject(presenter: AuthPresenter)
    fun inject(presenter: RegistrationPresenter)
    fun inject(presenter: RestorePasswordPresenter)

    //Settings presenters
    fun inject(presenterApp: AppSettingsPresenter)

    fun inject(presenter: ProfileSettingsPresenter)

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
    fun inject(tapeFragment: TapeFragment)
    fun inject(postsListFragment: PostsListFragment)
    fun inject(authActivity: AuthActivity)
    fun inject(myCommentsFragment: MyCommentsFragment)
    fun inject(profileFragment: ProfileFragment)
    fun inject(settingsFragment: SettingsFragment)
    fun inject(appSettingsFragment: AppSettingsFragment)

}