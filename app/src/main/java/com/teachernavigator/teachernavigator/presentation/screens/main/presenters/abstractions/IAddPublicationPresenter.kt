package com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions

import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.AddPublicationView

/**
 * Created by root on 20.09.17.
 */
interface IAddPublicationPresenter : ViewAttacher<AddPublicationView> {

    fun doPublic(post: Post)
    fun preview(post: Post)
}