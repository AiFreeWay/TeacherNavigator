package com.teachernavigator.teachernavigator.presentation.screens.common.comment

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.ICommentControllerFacade

/**
 * Created by root on 13.09.17.
 */
class PostCommentView : CommentView {

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, commentControllerFacade: ICommentControllerFacade) : super(context, commentControllerFacade)

    init {
        mLlOpenBranch.visibility = View.GONE
    }
}