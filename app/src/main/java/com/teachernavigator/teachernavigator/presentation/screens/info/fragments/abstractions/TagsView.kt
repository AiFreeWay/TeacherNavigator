package com.teachernavigator.teachernavigator.presentation.screens.info.fragments.abstractions

import com.teachernavigator.teachernavigator.domain.models.Tag
import com.teachernavigator.teachernavigator.presentation.screens.common.ChildView

/**
 * Created by Arthur Korchagin on 10.10.17
 */
interface TagsView : ChildView {

    companion object {
        const val ALL_TABS = 0
        const val TRENDS = 1
    }

    fun setTags(tags: List<Tag>)
    fun showRefresh()
    fun hideRefresh()

}