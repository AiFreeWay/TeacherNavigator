package com.teachernavigator.teachernavigator.presentation.adapters.holders.menu_holders

import android.content.Context
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
abstract class BaseMenuHolder: BaseHolder<MenuItem> {

    protected var mObserverEmitFromHolder: Observer<MenuData<*>>? = null
    protected var mObservableEmitToHolder: Observable<MenuData<*>>? = null

    constructor(context: Context) : super(View(context), null) {
    }

    constructor(itemView: View) : super(itemView, null) {
    }

    override abstract fun create(viewGroup: ViewGroup): BaseHolder<MenuItem>

    fun subscribeOnEmitInHolder(observable: Observable<MenuData<*>>) {
        mObservableEmitToHolder = observable
    }

    fun observOnEmitFromHolder(observer: Observer<MenuData<*>>) {
        mObserverEmitFromHolder = observer
    }
}