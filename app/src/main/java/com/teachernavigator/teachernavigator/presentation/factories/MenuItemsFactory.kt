package com.teachernavigator.teachernavigator.presentation.factories

import android.content.Context
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.models.MenuItem

/**
 * Created by root on 15.08.17.
 */
class MenuItemsFactory {

    companion object {

        fun getAuthMenuItemsFor(context: Context): ArrayList<MenuItem> {
            val items = ArrayList<MenuItem>()
            items.add(MenuItem(MenuItemTypes.LOGIN.id, context.getString(R.string.enter)))
            items.add(MenuItem(MenuItemTypes.DEVIDER.id))
            items.add(MenuItem(MenuItemTypes.TAPE.id, context.getString(R.string.information_tape)))
            items.add(MenuItem(MenuItemTypes.SETTINGS.id, context.getString(R.string.Settings)))
            items.add(MenuItem(MenuItemTypes.TAGS.id, context.getString(R.string.tags)))
            items.add(MenuItem(MenuItemTypes.ABOUT.id, context.getString(R.string.about)))
            items.add(MenuItem(MenuItemTypes.DEVIDER.id))
            items.add(MenuItem(MenuItemTypes.ADD_PUBLICATION.id, context.getString(R.string.add_publication)))
            return items
        }

        fun getNotAuthMenuItemsFor(context: Context): ArrayList<MenuItem> {
            val items = ArrayList<MenuItem>()
            items.add(MenuItem(MenuItemTypes.PROFILE_HEADER.id))
            items.add(MenuItem(MenuItemTypes.TAPE.id, context.getString(R.string.information_tape)))

            items.add(MenuItem(MenuItemTypes.MY_PUBLICATION.id, context.getString(R.string.my_publication)))
            items.add(MenuItem(MenuItemTypes.MY_COMMENTS.id, context.getString(R.string.my_comments)))
            items.add(MenuItem(MenuItemTypes.SAVED.id, context.getString(R.string.saved)))
            items.add(MenuItem(MenuItemTypes.CHAT.id, context.getString(R.string.chat)))
            items.add(MenuItem(MenuItemTypes.SUPPORT.id, context.getString(R.string.support)))
            items.add(MenuItem(MenuItemTypes.IMPORTATNT_TO_KNOW.id, context.getString(R.string.important_to_know)))
            items.add(MenuItem(MenuItemTypes.BAKN_OF_VACANCY.id, context.getString(R.string.bank_of_vacancy)))
            items.add(MenuItem(MenuItemTypes.DEVIDER.id))

            items.add(MenuItem(MenuItemTypes.SETTINGS.id, context.getString(R.string.Settings)))
            items.add(MenuItem(MenuItemTypes.TAGS.id, context.getString(R.string.tags)))
            items.add(MenuItem(MenuItemTypes.ABOUT.id, context.getString(R.string.about)))
            items.add(MenuItem(MenuItemTypes.ADD_PUBLICATION.id, context.getString(R.string.add_publication)))
            return items
        }
    }

    enum class MenuItemTypes(var id: Int) {
        LOGIN(0),

        TAPE(1),
        SETTINGS(2),
        TAGS(3),
        ABOUT(4),
        ADD_PUBLICATION(5),

        PROFILE_HEADER(6),
        MY_PUBLICATION(7),
        MY_COMMENTS(8),
        SAVED(9),
        CHAT(10),
        SUPPORT(11),
        IMPORTATNT_TO_KNOW(12),
        BAKN_OF_VACANCY(13),

        DEVIDER(100)
    }
}