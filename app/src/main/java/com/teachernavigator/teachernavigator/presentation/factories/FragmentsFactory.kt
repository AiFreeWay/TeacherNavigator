package com.teachernavigator.teachernavigator.presentation.factories

import android.os.Bundle
import android.support.v4.app.Fragment
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.AuthFragment
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.RegistrationFragment
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.RestorePasswordFragment
import com.teachernavigator.teachernavigator.presentation.screens.info.fragments.*
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.*
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.*
import com.teachernavigator.teachernavigator.presentation.screens.tape.fragments.PostsListFragment
import com.teachernavigator.teachernavigator.presentation.screens.tape.fragments.PostsSearchFragment

/**
 * Created by root on 14.08.17
 */
class FragmentsFactory {

    companion object {

        fun createFragment(screenKey: String?): Fragment =
                createFragment(screenKey, null)

        fun createFragment(screenKey: String?, bundle: Bundle?): Fragment =
                when (screenKey) {
                    TapeFragment.FRAGMENT_KEY -> addBundle(TapeFragment(), bundle)
                    AuthFragment.FRAGMENT_KEY -> addBundle(AuthFragment(), bundle)
                    RegistrationFragment.FRAGMENT_KEY -> addBundle(RegistrationFragment(), bundle)
                    AgreementFragment.FRAGMENT_KEY -> addBundle(AgreementFragment(), bundle)
                    RestorePasswordFragment.FRAGMENT_KEY -> addBundle(RestorePasswordFragment(), bundle)
                    MyCommentsFragment.FRAGMENT_KEY -> addBundle(MyCommentsFragment(), bundle)
                    PostsSearchFragment.FRAGMENT_KEY -> addBundle(PostsSearchFragment(), bundle)
                    SettingsFragment.FRAGMENT_KEY -> addBundle(SettingsFragment(), bundle)
                    PostsListFragment.FRAGMENT_KEY -> addBundle(PostsListFragment(), bundle)
                    JobsBankFragment.FRAGMENT_KEY -> addBundle(JobsBankFragment(), bundle)
                    MyVacanciesFragment.FRAGMENT_KEY -> addBundle(MyVacanciesFragment(), bundle)
                    VacanciesFragment.FRAGMENT_KEY -> addBundle(VacanciesFragment(), bundle)
                    CreateResumeFragment.FRAGMENT_KEY -> addBundle(CreateResumeFragment(), bundle)
                    VacancyFragment.FRAGMENT_KEY -> addBundle(VacancyFragment(), bundle)
                    ResumeListFragment.FRAGMENT_KEY -> addBundle(ResumeListFragment(), bundle)
                    MyResumeFragment.FRAGMENT_KEY -> addBundle(MyResumeFragment(), bundle)
                    CreateVacancyFragment.FRAGMENT_KEY -> addBundle(CreateVacancyFragment(), bundle)
                    AddPublicationFragment.FRAGMENT_KEY -> addBundle(AddPublicationFragment(), bundle)
                    ProfileFragment.FRAGMENT_KEY -> addBundle(ProfileFragment(), bundle)
                    ImportantToKnowFragment.FRAGMENT_KEY -> addBundle(ImportantToKnowFragment(), bundle)
                    ChatFragment.FRAGMENT_KEY -> addBundle(ChatFragment(), bundle)
                    PostCommentsFragment.FRAGMENT_KEY -> addBundle(PostCommentsFragment(), bundle)
                    PreviewFragment.FRAGMENT_KEY -> addBundle(PreviewFragment(), bundle)
                    AskSpecialistFragment.FRAGMENT_KEY -> addBundle(AskSpecialistFragment(), bundle)
                    SupportFragment.FRAGMENT_KEY -> addBundle(SupportFragment(), bundle)
                    AboutFragment.FRAGMENT_KEY -> addBundle(AboutFragment(), bundle)
                    TagsFragment.FRAGMENT_KEY -> addBundle(TagsFragment(), bundle)
                    else -> throw Exception("Invalid fragment key $screenKey FragmentsFactory.createFragment(screenKey: String?)")
                }

        private fun addBundle(fragment: Fragment, bundle: Bundle?): Fragment {
            fragment.arguments = bundle
            return fragment
        }
    }
}