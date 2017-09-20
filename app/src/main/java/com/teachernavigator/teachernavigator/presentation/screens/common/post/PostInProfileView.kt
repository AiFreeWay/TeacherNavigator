package com.teachernavigator.teachernavigator.presentation.screens.common.post

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacade

/**
 * Created by root on 20.09.17.
 */
class PostInProfileView : PostView {

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, postControllerFacade: IPostControllerFacade) : super(context, postControllerFacade)

    init {
        mTvComplain.visibility = View.GONE
        mIvSave.visibility = View.INVISIBLE
        mIvSubscribe.visibility = View.INVISIBLE
        mTvText.maxLines = 4
    }
}