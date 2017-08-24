package com.teachernavigator.teachernavigator.presentation.adapters.holders.menu_holders

import android.view.View
import android.view.ViewGroup
import com.teachernavigator.teachernavigator.presentation.adapters.holders.BaseHolder
import com.teachernavigator.teachernavigator.presentation.models.MenuData
import com.teachernavigator.teachernavigator.presentation.models.MenuItem
import io.reactivex.Observable
import io.reactivex.Observer

/**
 * Created by root on 16.08.17.
 */
abstract class BaseMenuHolder(itemView: View) : BaseHolder<MenuItem>(itemView, null) {

    protected var mInputChannel: Observable<MenuData<*>>? = null
    protected var mOutputChannel: Observer<MenuData<*>>? = null

    override abstract fun create(viewGroup: ViewGroup): BaseHolder<MenuItem>

    fun setInputChannel(observable: Observable<MenuData<*>>) {
        mInputChannel = observable
    }

    fun setOutputChannel(observer: Observer<MenuData<*>>) {
        mOutputChannel = observer
    }
}