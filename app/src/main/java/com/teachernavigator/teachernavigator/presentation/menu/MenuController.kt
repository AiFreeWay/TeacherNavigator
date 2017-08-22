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
    private lateinit var mActionsEmitterInHolderFromPresenter: PublishSubject<MenuData<*>>
    private lateinit var mActionsEmitterInPresenterFromHolder: PublishSubject<MenuData<*>>

    companion object {

        fun createControllerForAuthorizationUser(lifeCycleOwner: LifecycleRegistryOwner, context: Context): MenuController {
            val menuController = MenuController(lifeCycleOwner.lifecycle, MenuItemsFactory.getAuthMenuItemsFor(context))
            menuController.mAdapterStrategy = MenuAdapterStrategy(menuController)
            return menuController
        }

        fun createControllerForNotAuthorizationUser(lifeCycleOwner: LifecycleRegistryOwner, context: Context): MenuController {
            val menuController = MenuController(lifeCycleOwner.lifecycle, MenuItemsFactory.getNotAuthMenuItemsFor(context))
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
        mActionsEmitterInHolderFromPresenter = PublishSubject.create<MenuData<*>>()
        mActionsEmitterInPresenterFromHolder = PublishSubject.create<MenuData<*>>()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mActionsEmitterInHolderFromPresenter.onComplete()
        mActionsEmitterInPresenterFromHolder.onComplete()
    }

    fun loadToRecycleView(recylerView: RecyclerView) {
        val adapter = StrategyMultiRvAdapter<MenuItem>(mAdapterStrategy)
        recylerView.adapter = adapter
        adapter.loadData(mMenuItems)
    }

    fun <T> sendDataToHolder(menuData: MenuData<T>) {
        mActionsEmitterInHolderFromPresenter.onNext(menuData)
    }

    fun getSubscriberFromHolder(): Observable<MenuData<*>> = mActionsEmitterInPresenterFromHolder

    fun getEmitterInPresenter(): Observer<MenuData<*>> = mActionsEmitterInPresenterFromHolder
    fun getSubscriberFromPresenter(): Observable<MenuData<*>> = mActionsEmitterInHolderFromPresenter
}