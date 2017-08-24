package com.teachernavigator.teachernavigator.presentation.menu

import android.arch.lifecycle.*
import android.content.Context
import android.support.v7.widget.RecyclerView
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.presentation.adapters.AdapterStrategy
import com.teachernavigator.teachernavigator.presentation.adapters.StrategyMultiRvAdapter
import com.teachernavigator.teachernavigator.presentation.factories.MenuItemsFactory
import com.teachernavigator.teachernavigator.presentation.menu.abstractions.IHolderChannel
import com.teachernavigator.teachernavigator.presentation.menu.abstractions.IPresenterChannel
import com.teachernavigator.teachernavigator.presentation.models.MenuData
import com.teachernavigator.teachernavigator.presentation.models.MenuItem
import io.reactivex.subjects.PublishSubject

/**
 * Created by root on 15.08.17.
 */
class MenuController private constructor(lifeCycle: LifecycleRegistry, private val mMenuItems: ArrayList<MenuItem>) : LifecycleObserver {

    private lateinit var  mAdapterStrategy: AdapterStrategy<MenuItem>
    private val mHolderChannel: HolderChannel = HolderChannel()
    private val mPresenterChannel: PresenterChannel = PresenterChannel()

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
        val inHolderFromPresenter = PublishSubject.create<MenuData<*>>()
        val inPresenterFromHolder = PublishSubject.create<MenuData<*>>()

        mHolderChannel.setSubjects(inHolderFromPresenter, inPresenterFromHolder)
        mPresenterChannel.setSubjects(inHolderFromPresenter, inPresenterFromHolder)
    }

    fun loadToRecycleView(recylerView: RecyclerView) {
        val adapter = StrategyMultiRvAdapter<MenuItem>(mAdapterStrategy)
        recylerView.adapter = adapter
        adapter.loadData(mMenuItems)
    }

    fun getHolderChannel(): IHolderChannel = mHolderChannel

    fun getPresenterChannel(): IPresenterChannel = mPresenterChannel
}