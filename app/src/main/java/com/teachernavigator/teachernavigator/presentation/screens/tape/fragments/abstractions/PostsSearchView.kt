package com.teachernavigator.teachernavigator.presentation.screens.tape.fragments.abstractions

import com.teachernavigator.teachernavigator.domain.models.Tag
import com.teachernavigator.teachernavigator.presentation.screens.common.ChildView

/**
 * Created by root on 06.09.17
 */
interface PostsSearchView : ChildView {


    fun setSearchTags(tags: List<Tag>)
    fun setTags(tags: List<String>)
    fun setText(text: CharSequence)
    fun setChecked(publicationsContent: Pair<Boolean, Boolean>)
}