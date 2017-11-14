package com.teachernavigator.teachernavigator.presentation.menu.bind_strategy

import android.view.ViewGroup
import com.teachernavigator.teachernavigator.presentation.menu.binders.*
import com.teachernavigator.teachernavigator.presentation.factories.MenuItemsFactory
import com.teachernavigator.teachernavigator.presentation.menu.MenuController
import com.teachernavigator.teachernavigator.presentation.menu.binders.BaseMenuBinder

/**
 * Created by root on 15.08.17
 */
class MenuBindStrategy(private val mMenuController: MenuController) : BindStrategy {

    override fun createBinder(parent: ViewGroup, type: Int): BaseMenuBinder {
        val binder = when(type) {
            MenuItemsFactory.MenuItemTypes.LOGIN.id -> LoginBinder(parent)
            MenuItemsFactory.MenuItemTypes.PROFILE_HEADER.id -> HeaderBinder(parent)
            MenuItemsFactory.MenuItemTypes.ADD_PUBLICATION.id -> BottomBinder(parent)
            MenuItemsFactory.MenuItemTypes.DEVIDER.id -> DividerBinder(parent)
            MenuItemsFactory.MenuItemTypes.TAPE.id -> ItemBinder(parent)
            MenuItemsFactory.MenuItemTypes.SETTINGS.id -> ItemBinder(parent)
            MenuItemsFactory.MenuItemTypes.MY_PUBLICATION.id -> ItemBinder(parent)
            MenuItemsFactory.MenuItemTypes.MY_COMMENTS.id -> ItemBinder(parent)
            MenuItemsFactory.MenuItemTypes.SAVED.id -> ItemBinder(parent)
            MenuItemsFactory.MenuItemTypes.CHAT.id -> ItemBinder(parent)
            MenuItemsFactory.MenuItemTypes.SUPPORT.id -> ItemBinder(parent)
            MenuItemsFactory.MenuItemTypes.IMPORTANT_TO_KNOW.id -> ItemBinder(parent)
            MenuItemsFactory.MenuItemTypes.BANK_OF_VACANCY.id -> ItemBinder(parent)
            else -> throw Exception("Invalid menu item type $type MenuBindStrategy.getAdapterStrategy()")
        }

        if (type != MenuItemsFactory.MenuItemTypes.DEVIDER.id) {
            binder.setInputChannel(mMenuController.getHolderChannel().getInputChannel())
            binder.setOutputChannel(mMenuController.getHolderChannel().getOutputChannel())
        }
        return binder
    }
}