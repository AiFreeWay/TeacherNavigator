package com.teachernavigator.teachernavigator.presentation.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.presentation.factories.FragmentsFactory
import ru.terrakok.cicerone.android.SupportFragmentNavigator

/**
 * Created by root on 14.08.17.
 */
class FragmentNavigator(private val mActvity: AppCompatActivity, fragmentManager: FragmentManager, fragmentLayoutId: Int) : SupportFragmentNavigator(fragmentManager, fragmentLayoutId) {

    override fun createFragment(screenKey: String?, data: Any?): Fragment =
            FragmentsFactory.createFragment(screenKey)

    override fun exit() = mActvity.finish()

    override fun showSystemMessage(message: String?) = Logger.logDebug(message)
}