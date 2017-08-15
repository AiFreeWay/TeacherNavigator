package com.teachernavigator.teachernavigator.presentation.utils.menu_controller

import android.content.Context
import android.support.v7.widget.RecyclerView
import com.teachernavigator.teachernavigator.presentation.adapters.StrategyMultiRvAdapter
import com.teachernavigator.teachernavigator.presentation.factories.MenuItemsFactory
import com.teachernavigator.teachernavigator.presentation.models.MenuItem
import com.teachernavigator.teachernavigator.presentation.utils.menu_controller.item_controllers.abstractions.MenuItemsFacade
import com.teachernavigator.teachernavigator.presentation.utils.menu_controller.item_controllers.AuthMenuItemsController
import com.teachernavigator.teachernavigator.presentation.utils.menu_controller.item_controllers.NotAuthMenuItemsController

/**
 * Created by root on 15.08.17.
 */
class MenuController private constructor(private val mItemsController: MenuItemsFacade, menuItems: ArrayList<MenuItem>) {

    companion object {

        fun createControllerForAithorizationUser(context: Context): MenuController {
            return MenuController(AuthMenuItemsController(), MenuItemsFactory.getAuthMenuItemsFor(context))
        }

        fun createControllerForNotAithorizationUser(context: Context): MenuController {
            return MenuController(NotAuthMenuItemsController(), MenuItemsFactory.getNotAuthMenuItemsFor(context))
        }
    }

    init {
        mItemsController.init(menuItems)
    }

    fun loadToRecycleView(recylerView: RecyclerView) {
        val adapter = StrategyMultiRvAdapter<MenuItem>(mItemsController.getAdapterStrategy())
        recylerView.adapter = adapter
        adapter.loadData(mItemsController.getItems())
    }

    fun getItemsController(): MenuItemsFacade = mItemsController
}