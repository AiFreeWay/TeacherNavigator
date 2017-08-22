package com.teachernavigator.teachernavigator.presentation.factories

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.models.ViewPagerItemContainer
import com.teachernavigator.teachernavigator.presentation.screens.tape.fragments.TapeListFragment
import com.teachernavigator.teachernavigator.presentation.utils.TapeStrategy

/**
 * Created by root on 17.08.17.
 */
class TapeFragmentsFactory {

    companion object {

        fun createItems(context: Context): List<ViewPagerItemContainer> {
            val itemContainers = ArrayList<ViewPagerItemContainer>()
            itemContainers.add(ViewPagerItemContainer(context.getString(R.string.last), addTapeType(TapeStrategy.TAPE_TYPE_LAST, TapeListFragment())))
            itemContainers.add(ViewPagerItemContainer(context.getString(R.string.best), addTapeType(TapeStrategy.TAPE_TYPE_BEST, TapeListFragment())))
            itemContainers.add(ViewPagerItemContainer(context.getString(R.string.news), addTapeType(TapeStrategy.TAPE_TYPE_NEWS, TapeListFragment())))
            itemContainers.add(ViewPagerItemContainer(context.getString(R.string.interviews), addTapeType(TapeStrategy.TAPE_TYPE_INTERVIEWS, TapeListFragment())))
            return itemContainers
        }

        fun addTapeType(tapeType: Int, fagment: Fragment): Fragment {
            val bundle = Bundle()
            bundle.putInt(TapeListFragment.TAPE_TYPE_KEY, tapeType)
            fagment.arguments = bundle
            return fagment
        }
    }
}