package com.teachernavigator.teachernavigator.presentation.utils.menu_controller.item_controllers.abstractions

import com.teachernavigator.teachernavigator.presentation.adapters.AdapterStrategy
import com.teachernavigator.teachernavigator.presentation.models.MenuItem

/**
 * Created by root on 15.08.17.
 */
interface MenuItemsFacade {

    fun init(menuItems: ArrayList<MenuItem>)
    fun getItems(): List<MenuItem>
    fun getAdapterStrategy(): AdapterStrategy<MenuItem>

    fun sendData(type: Int)
}