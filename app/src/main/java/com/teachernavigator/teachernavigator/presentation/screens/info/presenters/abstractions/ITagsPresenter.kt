package com.teachernavigator.teachernavigator.presentation.screens.info.presenters.abstractions

import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.info.fragments.abstractions.TagsView

/**
 * Created by Arthur Korchagin on 10.10.17
 */
interface ITagsPresenter : ViewAttacher<TagsView> {

    fun refresh()
    var trends: Boolean
    var text: CharSequence

}