package com.teachernavigator.teachernavigator.presentation.screens.tape.fragments.abstractions

import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.screens.base.ChildView

/**
 * Created by root on 18.08.17.
 */
interface TapeListView : ChildView {

    fun loadPosts(posts: List<Post>)
}