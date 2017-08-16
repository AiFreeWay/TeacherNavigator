package com.teachernavigator.teachernavigator.presentation.menu

import android.arch.lifecycle.*
import android.content.Context
import android.support.v7.widget.RecyclerView
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.presentation.adapters.AdapterStrategy
import com.teachernavigator.teachernavigator.presentation.adapters.StrategyMultiRvAdapter
import com.teachernavigator.teachernavigator.presentation.factories.MenuItemsFactory
import com.teachernavigator.teachernavigator.presentation.models.MenuData
import com.teachernavigator.teachernavigator.presentation.models.MenuItem
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.subjects.PublishSubject

/**
 * Created by root on 15.08.17.
 */
class MenuController private constructor(lifeCycle: LifecycleRegistry, private val mMenuItems: ArrayList<MenuItem>) : LifecycleObserver {

    private lateinit var  mAdapterStrategy: AdapterStrategy<MenuItem>
    private lateinit var mActionsEmitterInHolder: PublishSubject<MenuData<*>>
    private lateinit var mActionsEmitterFromHolder: PublishSubject<MenuData<*>>

    companion object {

        fun createControllerForAithorizationUser(lifeCycleOwner: LifecycleRegistryOwner, context: Context): MenuController {
            val menuController = MenuController(lifeCycleOwner.lifecycle, MenuItemsFactory.getAuthMenuItemsFor(context))
            menuController.mAdapterStrategy = MenuAdapterStrategy(menuController)
            return menuController
        }

        fun createControllerForNotAithorizationUser(lifeCycleOwner: LifecycleRegistryOwner, context: Context): MenuController {
            val menuController = MenuController(lifeCycleOwner.lifecycle, MenuItemsFactory.getAuthMenuItemsFor(context))
            menuController.mAdapterStrategy = MenuAdapterStrategy(menuController)
            return menuController
        }
    }

    init {
        if (BuildConfig.DEBUG) Logger.logDebug("create CONTROLLER MenuController")
        lifeCycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        mActionsEmitterInHolder = PublishSubject.create<MenuData<*>>()
        mActionsEmitterFromHolder = PublishSubject.create<MenuData<*>>()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mActionsEmitterInHolder.onComplete()
        mActionsEmitterFromHolder.onComplete()
    }

    fun loadToRecycleView(recylerView: RecyclerView) {
        val adapter = StrategyMultiRvAdapter<MenuItem>(mAdapterStrategy)
        recylerView.adapter = adapter
        adapter.loadData(mMenuItems)
    }

    fun <T> sendDataToHolder(menuData: MenuData<T>) {
        mActionsEmitterInHolder.onNext(menuData)
    }

    fun getActionEmitterInHolder(): Observable<MenuData<*>> = mActionsEmitterInHolder

    fun getActionEmitterFromHolder(): Observer<MenuData<*>> = mActionsEmitterFromHolder
}