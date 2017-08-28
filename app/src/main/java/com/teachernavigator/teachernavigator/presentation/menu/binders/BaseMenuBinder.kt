package com.teachernavigator.teachernavigator.presentation.menu.binders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teachernavigator.teachernavigator.presentation.models.MenuData
import com.teachernavigator.teachernavigator.presentation.models.MenuItem
import io.reactivex.Observable
import io.reactivex.Observer

/**
 * Created by root on 28.08.17.
 */
abstract class BaseMenuBinder(protected val mView: View) {

    companion object {

        fun viewInflater(viewGroup: ViewGroup, resLayout: Int): View {
            val layoutInflater = LayoutInflater.from(viewGroup.context)
            return layoutInflater.inflate(resLayout, viewGroup, false)
        }
    }

    fun setInputChannel(observable: Observable<MenuData<*>>) {
        mInputChannel = observable
    }

    fun setOutputChannel(observer: Observer<MenuData<*>>) {
        mOutputChannel = observer
    }

    fun getView() = mView

    protected var mInputChannel: Observable<MenuData<*>>? = null

    protected var mOutputChannel: Observer<MenuData<*>>? = null

    abstract fun bind(menuItem: MenuItem)
}