package com.teachernavigator.teachernavigator.presentation.facades.abstractions

import android.widget.ImageView

/**
 * Created by root on 20.09.17.
 */
interface IProfileFacade {

    fun onExit()

    fun onChangePhoto(imageView: ImageView)
}