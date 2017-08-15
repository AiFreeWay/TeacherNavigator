package com.teachernavigator.teachernavigator.presentation.utils.menu_controller.item_controllers

import android.view.ViewGroup
import com.teachernavigator.teachernavigator.presentation.adapters.AdapterStrategy
import com.teachernavigator.teachernavigator.presentation.adapters.holders.BaseHolder
import com.teachernavigator.teachernavigator.presentation.models.MenuItem
import com.teachernavigator.teachernavigator.presentation.utils.menu_controller.item_controllers.abstractions.MenuItemsFacade

/**
 * Created by root on 15.08.17.
 */
class NotAuthMenuItemsController : MenuItemsFacade {

    lateinit var mMenuItems: ArrayList<MenuItem>

    override fun init(menuItems: ArrayList<MenuItem>) {
        mMenuItems = menuItems
    }

    override fun getItems(): List<MenuItem> = mMenuItems

    override fun getAdapterStrategy(): AdapterStrategy<MenuItem> =
            object : AdapterStrategy<MenuItem> {
                override fun createHolder(parent: ViewGroup, type: Int): BaseHolder<MenuItem> {
                    when(type) {
                        else -> throw Exception("Invalid menu item type NotAuthMenuItemsController.getAdapterStrategy()")
                    }
                }
            }

    override fun sendData(type: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}