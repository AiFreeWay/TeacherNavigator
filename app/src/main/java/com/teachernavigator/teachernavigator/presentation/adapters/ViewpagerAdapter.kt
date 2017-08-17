package com.teachernavigator.teachernavigator.presentation.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.teachernavigator.teachernavigator.presentation.models.ViewPagerItemContainer

/**
 * Created by root on 17.08.17.
 */
class ViewpagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private var mData: List<ViewPagerItemContainer> = emptyList<ViewPagerItemContainer>()

    override fun getItem(position: Int): Fragment? {
        return mData.get(position).mFragment
    }

    override fun getCount(): Int {
        return mData.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mData.get(position).mTabTitle
    }

    fun loadData(data: List<ViewPagerItemContainer>) {
        mData = data
        notifyDataSetChanged()
    }
}


