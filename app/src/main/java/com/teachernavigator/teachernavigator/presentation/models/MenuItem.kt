package com.teachernavigator.teachernavigator.presentation.models

/**
 * Created by root on 15.08.17.
 */
data class MenuItem(val mType: Int, var mTitle: String?) : Typed {

    constructor(type: Int): this(type, null)

    override fun getType(): Int = mType
}