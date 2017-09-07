package com.teachernavigator.teachernavigator.presentation.models

/**
 * Created by root on 16.08.17.
 */
data class MenuData<T>(val mType: Int, val mData: T?) : Typed  {

    override fun getType(): Int = mType
}