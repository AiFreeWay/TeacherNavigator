package com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions

import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.AddPublicationView
import java.io.File

/**
 * Created by root on 20.09.17
 */
interface IAddPublicationPresenter : ViewAttacher<AddPublicationView> {

    fun publish(title: CharSequence, text: CharSequence)
    fun preview(title: CharSequence, text: CharSequence)

    fun addTag(tag: CharSequence)
    fun searchTags(tag: CharSequence)
    fun setFile(file: File?, mimeType: String?)
}