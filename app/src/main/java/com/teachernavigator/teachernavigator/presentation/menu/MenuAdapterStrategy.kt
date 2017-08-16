package com.teachernavigator.teachernavigator.presentation.menu

import android.view.ViewGroup
import com.teachernavigator.teachernavigator.presentation.adapters.AdapterStrategy
import com.teachernavigator.teachernavigator.presentation.adapters.holders.menu_holders.DeviderHolder
import com.teachernavigator.teachernavigator.presentation.adapters.holders.menu_holders.BaseMenuHolder
import com.teachernavigator.teachernavigator.presentation.adapters.holders.menu_holders.BottomHolder
import com.teachernavigator.teachernavigator.presentation.adapters.holders.menu_holders.ItemHolder
import com.teachernavigator.teachernavigator.presentation.adapters.holders.menu_holders.LoginHolder
import com.teachernavigator.teachernavigator.presentation.factories.MenuItemsFactory
import com.teachernavigator.teachernavigator.presentation.models.MenuItem

/**
 * Created by root on 15.08.17.
 */
class MenuAdapterStrategy(private val mMenuController: MenuController) : AdapterStrategy<MenuItem> {

    override fun createHolder(parent: ViewGroup, type: Int): BaseMenuHolder {
        val holder  = when(type) {
            MenuItemsFactory.MenuItemTypes.LOGIN.id -> LoginHolder(parent)
            MenuItemsFactory.MenuItemTypes.DEVIDER.id -> DeviderHolder(parent)
            MenuItemsFactory.MenuItemTypes.TAPE.id -> ItemHolder(parent)
            MenuItemsFactory.MenuItemTypes.SETTINGS.id -> ItemHolder(parent)
            MenuItemsFactory.MenuItemTypes.TAGS.id -> ItemHolder(parent)
            MenuItemsFactory.MenuItemTypes.ABOUT.id -> ItemHolder(parent)
            MenuItemsFactory.MenuItemTypes.ADD_PUBLICATION.id -> BottomHolder(parent)
            else -> throw Exception("Invalid menu item type $type MenuAdapterStrategy.getAdapterStrategy()")
        }

        if (type != MenuItemsFactory.MenuItemTypes.DEVIDER.id) {
            holder.subscribeOnEmitInHolder(mMenuController.getActionEmitterInHolder())
            holder.observOnEmitFromHolder(mMenuController.getActionEmitterFromHolder())
        }
        return holder
    }
}