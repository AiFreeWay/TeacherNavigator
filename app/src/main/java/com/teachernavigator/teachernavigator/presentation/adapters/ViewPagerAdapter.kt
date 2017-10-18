package com.teachernavigator.teachernavigator.presentation.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.teachernavigator.teachernavigator.presentation.models.ViewPagerItemContainer

/**
 * Created by root on 17.08.17
 */
class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    private var mData: List<ViewPagerItemContainer> = emptyList()

    override fun getItem(position: Int): Fragment? = mData[position].mFragment

    override fun getCount(): Int = mData.size

    override fun getPageTitle(position: Int): CharSequence = mData[position].mTabTitle

    fun loadData(data: List<ViewPagerItemContainer>) {
        mData = data
        notifyDataSetChanged()
    }

}


