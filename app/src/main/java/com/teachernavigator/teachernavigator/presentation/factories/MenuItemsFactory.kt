package com.teachernavigator.teachernavigator.presentation.factories

import android.content.Context
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.models.MenuItem

/**
 * Created by root on 15.08.17
 */
class MenuItemsFactory {

    companion object {

        fun getNotAuthMenuItemsFor(context: Context): ArrayList<MenuItem> {
            val items = ArrayList<MenuItem>()
            items.add(MenuItem(MenuItemTypes.LOGIN.id))
            items.add(MenuItem(MenuItemTypes.DEVIDER.id))
            items.add(MenuItem(MenuItemTypes.TAPE.id, context.getString(R.string.information_tape), R.drawable.ic_tape))
            items.add(MenuItem(MenuItemTypes.ADD_PUBLICATION.id))
            return items
        }

        fun getAuthMenuItemsFor(context: Context): ArrayList<MenuItem> {
            val items = ArrayList<MenuItem>()
            items.add(MenuItem(MenuItemTypes.PROFILE_HEADER.id))
            items.add(MenuItem(MenuItemTypes.DEVIDER.id))
            items.add(MenuItem(MenuItemTypes.TAPE.id, context.getString(R.string.information_tape), R.drawable.ic_tape))

            items.add(MenuItem(MenuItemTypes.MY_PUBLICATION.id, context.getString(R.string.my_publication), R.drawable.ic_my_publication))
            items.add(MenuItem(MenuItemTypes.MY_COMMENTS.id, context.getString(R.string.my_comments), R.drawable.ic_my_comments))
            items.add(MenuItem(MenuItemTypes.SAVED.id, context.getString(R.string.saved), R.drawable.ic_saved))
            items.add(MenuItem(MenuItemTypes.CHAT.id, context.getString(R.string.chat), R.drawable.ic_chat))
            items.add(MenuItem(MenuItemTypes.SUPPORT.id, context.getString(R.string.support), R.drawable.ic_support))
            items.add(MenuItem(MenuItemTypes.IMPORTANT_TO_KNOW.id, context.getString(R.string.important_to_know), R.drawable.ic_important_to_know))
            items.add(MenuItem(MenuItemTypes.BANK_OF_VACANCY.id, context.getString(R.string.bank_of_vacancy), R.drawable.ic_bank_of_vacancy))

            items.add(MenuItem(MenuItemTypes.ADD_PUBLICATION.id))
            return items
        }
    }

    enum class MenuItemTypes {
        LOGIN,

        TAPE,
        SETTINGS,
        ADD_PUBLICATION,

        PROFILE_HEADER,
        MY_PUBLICATION,
        MY_COMMENTS,
        SAVED,
        CHAT,
        SUPPORT,
        IMPORTANT_TO_KNOW,
        BANK_OF_VACANCY,

        ABOUT,
        TAGS,

        DEVIDER;

        val id: Int
            get() = ordinal
    }
}