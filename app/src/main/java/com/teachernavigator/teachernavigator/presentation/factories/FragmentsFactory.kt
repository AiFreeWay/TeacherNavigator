package com.teachernavigator.teachernavigator.presentation.factories

import android.os.Bundle
import android.support.v4.app.Fragment
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.AuthFragment
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.RegistrationFragment
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.RestorePasswordFragment
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.MyCommentsFragment
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.MyPublicationsFragment
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.SavedPostsFragment
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.TapeFragment

/**
 * Created by root on 14.08.17.
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
                RestorePasswordFragment.FRAGMENT_KEY -> addBundle(RestorePasswordFragment(), bundle)
                MyCommentsFragment.FRAGMENT_KEY -> addBundle(MyCommentsFragment(), bundle)
                SavedPostsFragment.FRAGMENT_KEY -> addBundle(SavedPostsFragment(), bundle)
                MyPublicationsFragment.FRAGMENT_KEY -> addBundle(MyPublicationsFragment(), bundle)
                else -> throw Exception("Invalid fragment key $screenKey FragmentsFactory.createFragment(screenKey: String?)")
            }

        fun addBundle(fragment: Fragment, bundle: Bundle?): Fragment {
            fragment.arguments = bundle
            return fragment
        }
    }
}