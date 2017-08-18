package com.teachernavigator.teachernavigator.presentation.factories

import android.content.Context
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.models.ViewPagerItemContainer
import com.teachernavigator.teachernavigator.presentation.screens.tape.fragments.*

/**
 * Created by root on 17.08.17.
 */
class TapeFragmentsFactory {

    companion object {

        fun createItems(context: Context): List<ViewPagerItemContainer> {
            val itemContainers = ArrayList<ViewPagerItemContainer>()
            itemContainers.add(ViewPagerItemContainer(context.getString(R.string.last), LastFragment()))
            itemContainers.add(ViewPagerItemContainer(context.getString(R.string.best), BestFragment()))
            itemContainers.add(ViewPagerItemContainer(context.getString(R.string.news), NewsFragment()))
            itemContainers.add(ViewPagerItemContainer(context.getString(R.string.interviews), InterviewsFragment()))
            return itemContainers
        }
    }
}