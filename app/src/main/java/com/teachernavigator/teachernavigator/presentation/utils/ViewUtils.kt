package com.teachernavigator.teachernavigator.presentation.utils

import android.support.annotation.IdRes
import android.view.View

/**
 * Created by lliepmah on 24.09.17
 */

inline fun <reified T : View> View.find(@IdRes id: Int): T = findViewById(id)