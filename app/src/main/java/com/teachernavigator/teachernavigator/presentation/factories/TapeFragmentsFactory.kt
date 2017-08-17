package com.teachernavigator.teachernavigator.presentation.factories

import android.content.Context
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.models.ViewPagerItemContainer

/**
 * Created by root on 17.08.17.
 */
class TapeFragmentsFactory {

    companion object {

        fun createItems(context: Context): List<ViewPagerItemContainer> {
            val itemContainers = ArrayList<ViewPagerItemContainer>()
            itemContainers.add(ViewPagerItemContainer(context.getString(R.string.last), ))
            itemContainers.add(ViewPagerItemContainer(context.getString(R.string.best), ))
            itemContainers.add(ViewPagerItemContainer(context.getString(R.string.news), ))
            itemContainers.add(ViewPagerItemContainer(context.getString(R.string.Interviews), ))
            return itemContainers
        }
    }
}