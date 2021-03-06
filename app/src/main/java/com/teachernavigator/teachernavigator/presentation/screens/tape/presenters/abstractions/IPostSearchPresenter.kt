package com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.abstractions

import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.tape.fragments.abstractions.PostsSearchView

/**
 * Created by root on 06.09.17
 */
interface IPostSearchPresenter : ViewAttacher<PostsSearchView> {

    fun navigateBack()
    fun searchTags(tag: CharSequence)
    fun addTag(name: CharSequence)
    fun setSource(id: Int?)
    fun performSearch(text: CharSequence, publicationsContent: Pair<Boolean, Boolean>)

}