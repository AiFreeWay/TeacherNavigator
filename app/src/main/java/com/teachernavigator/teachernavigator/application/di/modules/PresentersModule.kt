package com.teachernavigator.teachernavigator.application.di.modules

import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.AuthParentPresenter
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.AuthPresenter
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.RegistrationPresenter
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.RestorePasswordPresenter
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.abstractions.IAuthParentPresenter
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.abstractions.IAuthPresenter
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.abstractions.IRegistrationPresenter
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.abstractions.IRestorePasswordPresenter
import com.teachernavigator.teachernavigator.presentation.screens.info.presenters.*
import com.teachernavigator.teachernavigator.presentation.screens.info.presenters.abstractions.*
import com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters.*
import com.teachernavigator.teachernavigator.presentation.screens.jobs.presenters.abstractions.*
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.*
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.*
import com.teachernavigator.teachernavigator.presentation.screens.settings.presenters.AppSettingsPresenter
import com.teachernavigator.teachernavigator.presentation.screens.settings.presenters.ProfileSettingsPresenter
import com.teachernavigator.teachernavigator.presentation.screens.settings.presenters.abstractions.IAppSettingsPresenter
import com.teachernavigator.teachernavigator.presentation.screens.settings.presenters.abstractions.IProfileSettingsPresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.PostSearchPresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.PostsListPresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.abstractions.IPostSearchPresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.abstractions.IPostsListPresenter
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
    fun provideResumeListPresetner(presenter: ResumeListPresenter): IResumeListPresetner = presenter

    @Provides
    @PerParentScreen
    fun provideJobsPresenter(presenter: VacanciesPresenter): IVacanciesPresenter = presenter

    @Provides
    @PerParentScreen
    fun provideVacancyPresenter(presenter: VacancyPresenter): IVacancyPresenter = presenter

    @Provides
    @PerParentScreen
    fun provideImportantToKnowPresenter(presenter: ImportantToKnowPresenter): IImportantToKnowPresenter = presenter

    @Provides
    @PerParentScreen
    fun provideAboutPresenter(presenter: AboutPresenter): IAboutPresenter = presenter

    @Provides
    @PerParentScreen
    fun provideSupportPresenter(presenter: SupportPresenter): ISupportPresenter = presenter

    @Provides
    @PerParentScreen
    fun provideAskSpecialistPresenter(presenter: AskSpecialistPresenter): IAskSpecialistPresenter = presenter

    @Provides
    @PerParentScreen
    fun providePostActionsController(presenter: PostActionsController): IPostActionsController = presenter

    @Provides
    @PerParentScreen
    fun providePostCommentsPresenter(presenter: PostCommentsPresenter): IPostCommentsPresenter = presenter

    @Provides
    @PerParentScreen
    fun provideTagsPresenter(presenter: TagsPresenter): ITagsPresenter = presenter

    @Provides
    @PerParentScreen
    fun provideAddPublicationPresenter(presenter: AddPublicationPresenter): IAddPublicationPresenter = presenter

    @Provides
    @PerParentScreen
    fun provideProfileSettingsPresenter(presenter: ProfileSettingsPresenter): IProfileSettingsPresenter = presenter

    @Provides
    @PerParentScreen
    fun provideRegistrationPresenter(presenter: RegistrationPresenter): IRegistrationPresenter = presenter

    @Provides
    @PerParentScreen
    fun provideAuthPresenter(presenter: AuthPresenter): IAuthPresenter = presenter

    @Provides
    @PerParentScreen
    fun provideRestorePasswordPresenter(presenter: RestorePasswordPresenter): IRestorePasswordPresenter = presenter

    @Provides
    @PerParentScreen
    fun providePostSearchPresenter(presenter: PostSearchPresenter): IPostSearchPresenter = presenter

    @Provides
    @PerParentScreen
    fun provideTapePresenter(presenter: TapePresenter): ITapePresenter = presenter

    @Provides
    @PerParentScreen
    fun providePostsListPresenter(presenter: PostsListPresenter): IPostsListPresenter = presenter

    @Provides
    @PerParentScreen
    fun provideAcAuthParentPresenter(presenter: AuthParentPresenter): IAuthParentPresenter = presenter

    @Provides
    @PerParentScreen
    fun provideMyCommentsPresenter(presenter: MyCommentsPresenter): IMyCommentsPresenter = presenter

    @Provides
    @PerParentScreen
    fun provideProfilePresenter(presenter: ProfilePresenter): IProfilePresenter = presenter

    @Provides
    @PerParentScreen
    fun provideSettingsPresenter(presenter: SettingsPresenter): ISettingsPresenter = presenter

    @Provides
    @PerParentScreen
    fun provideAppSettingsPresenter(presenter: AppSettingsPresenter): IAppSettingsPresenter = presenter

    @Provides
    @PerParentScreen
    fun provideChatPresenter(presenter: ChatPresenter): IChatPresenter = presenter

}