package com.teachernavigator.teachernavigator.presentation.factories

import android.content.Context
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.models.ViewPagerItemContainer
import com.teachernavigator.teachernavigator.presentation.screens.settings.fragments.AppSettingsFragment
import com.teachernavigator.teachernavigator.presentation.screens.settings.fragments.ProfileSettingsFragment

/**
 * Created by root on 18.09.17.
 */
class SettingsFragmentFactory {

    companion object {

        fun createItems(context: Context): List<ViewPagerItemContainer> {
            val itemContainers = ArrayList<ViewPagerItemContainer>()
            itemContainers.add(ViewPagerItemContainer(context.getString(R.string.application), AppSettingsFragment()))
            itemContainers.add(ViewPagerItemContainer(context.getString(R.string.profile), ProfileSettingsFragment()))
            return itemContainers
        }
    }
}