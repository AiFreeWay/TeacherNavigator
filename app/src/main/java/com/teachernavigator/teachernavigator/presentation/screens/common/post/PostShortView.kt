package com.teachernavigator.teachernavigator.presentation.screens.common.post

import android.content.Context
import android.util.AttributeSet
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacade

/**
 * Created by root on 20.09.17.
 */
class PostShortView : PostView {

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, postControllerFacade: IPostControllerFacade) : super(context, postControllerFacade)

    init {
        mTvText.maxLines = 4
    }
}