package com.teachernavigator.teachernavigator.presentation.factories

import android.os.Bundle
import android.support.v4.app.Fragment
import com.teachernavigator.teachernavigator.presentation.ui.main.fragments.TapeFragment

/**
 * Created by root on 14.08.17.
 */
class FragmentFactory {

    companion object {

        fun createFragment(screenKey: String?): Fragment =
                createFragment(screenKey, null)

        fun createFragment(screenKey: String?, bundle: Bundle?): Fragment =
            when (screenKey) {
                TapeFragment.FRAGMENT_KEY -> addBundle(TapeFragment(), bundle)
                else -> throw Exception("Invalid fragment key FragmentFactory.createFragment(screenKey: String?)")
            }

        fun addBundle(fragment: Fragment, bundle: Bundle?): Fragment {
            fragment.arguments = bundle
            return fragment
        }
    }
}