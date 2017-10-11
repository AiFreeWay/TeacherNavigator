package com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions

import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.AddPublicationView

/**
 * Created by root on 20.09.17
 */
interface IAddPublicationPresenter : ViewAttacher<AddPublicationView> {

    var filePath: String?
    var fileMime: String?

    fun publish(title: CharSequence, text: CharSequence)
    fun preview(title: CharSequence, text: CharSequence)

    fun addTag(tag: CharSequence)
    fun searchTags(tag: CharSequence)
}