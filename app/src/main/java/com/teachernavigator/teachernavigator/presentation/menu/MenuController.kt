package com.teachernavigator.teachernavigator.presentation.menu

import android.arch.lifecycle.*
import android.content.Context
import android.view.ViewGroup
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.presentation.factories.MenuItemsFactory
import com.teachernavigator.teachernavigator.presentation.menu.abstractions.BindStrategy
import com.teachernavigator.teachernavigator.presentation.menu.abstractions.IHolderChannel
import com.teachernavigator.teachernavigator.presentation.menu.abstractions.IPresenterChannel
import com.teachernavigator.teachernavigator.presentation.models.MenuData
import com.teachernavigator.teachernavigator.presentation.models.MenuItem
import io.reactivex.subjects.PublishSubject


/**
 * Created by root on 15.08.17.
 */
class MenuController private constructor(lifeCycle: LifecycleRegistry, private val mMenuItems: ArrayList<MenuItem>) : LifecycleObserver {

    private lateinit var  mBindStrategy: BindStrategy
    private val mHolderChannel: HolderChannel = HolderChannel()
    private val mPresenterChannel: PresenterChannel = PresenterChannel()

    companion object {

        fun createControllerForAuthorizationUser(lifeCycleOwner: LifecycleRegistryOwner, context: Context): MenuController {
            val menuController = MenuController(lifeCycleOwner.lifecycle, MenuItemsFactory.getAuthMenuItemsFor(context))
            menuController.mBindStrategy = MenuBindStrategy(menuController)
            return menuController
        }

        fun createControllerForNotAuthorizationUser(lifeCycleOwner: LifecycleRegistryOwner, context: Context): MenuController {
            val menuController = MenuController(lifeCycleOwner.lifecycle, MenuItemsFactory.getNotAuthMenuItemsFor(context))
            menuController.mBindStrategy = MenuBindStrategy(menuController)
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

    fun loadMenuItemsToViewGroup(viewGroup: ViewGroup) {
        viewGroup.removeAllViews()
        mMenuItems.forEach {
            createBinder(viewGroup, it)
        }
    }

    fun getHolderChannel(): IHolderChannel = mHolderChannel

    fun getPresenterChannel(): IPresenterChannel = mPresenterChannel

    private fun createBinder(viewGroup: ViewGroup, menuItem: MenuItem) {
        val binder = mBindStrategy.createBinder(viewGroup, menuItem.mType)
        binder.bind(menuItem)
        viewGroup.addView(binder.getView())
    }
}