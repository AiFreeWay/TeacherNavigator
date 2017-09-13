package com.teachernavigator.teachernavigator.presentation.screens.tape.activities.absctraction

import com.teachernavigator.teachernavigator.domain.models.Comment
import com.teachernavigator.teachernavigator.presentation.screens.common.ParentView

/**
 * Created by root on 07.09.17.
 */
interface PostCommentsView : ParentView {

    fun loadComment(comment: Comment)
    fun lockUi()
    fun unlockUi()
}