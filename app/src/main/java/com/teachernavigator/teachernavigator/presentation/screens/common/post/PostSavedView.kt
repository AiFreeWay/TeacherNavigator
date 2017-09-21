package com.teachernavigator.teachernavigator.presentation.screens.common.post

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacade

/**
 * Created by root on 13.09.17.
 */
class PostSavedView : PostView {

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, postControllerFacade: IPostControllerFacade) : super(context, postControllerFacade)

    init {
        mTvComplain.visibility = View.GONE
        mIvSave.visibility = View.INVISIBLE
        mTvText.maxLines = 4
    }
}