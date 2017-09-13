package com.teachernavigator.teachernavigator.presentation.screens.tape.activities.absctraction

import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.screens.common.ParentView

/**
 * Created by root on 30.08.17.
 */
interface PostDetailView : ParentView {

    fun loadPost(post: Post)
}