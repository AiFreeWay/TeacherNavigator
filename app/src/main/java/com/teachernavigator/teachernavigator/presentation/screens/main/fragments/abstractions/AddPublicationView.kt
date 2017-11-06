package com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions

import com.teachernavigator.teachernavigator.domain.models.Tag
import com.teachernavigator.teachernavigator.presentation.screens.common.ChildView

/**
 * Created by root on 20.09.17
 */
interface AddPublicationView : ChildView {

    fun setSearchTags(tags: List<Tag>)
    fun setTags(tags: List<String>)
    fun setFileName(fileName: String)

}