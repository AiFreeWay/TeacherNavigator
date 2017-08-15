package com.teachernavigator.teachernavigator.presentation.adapters

import android.view.ViewGroup
import com.teachernavigator.teachernavigator.presentation.adapters.holders.BaseHolder

/**
 * Created by root on 15.08.17.
 */
interface AdapterStrategy<M> {

    fun createHolder(parent: ViewGroup, type: Int): BaseHolder<M>
}