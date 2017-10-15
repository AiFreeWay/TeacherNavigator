package com.teachernavigator.teachernavigator.application.utils

import android.app.Activity
import android.support.v4.app.Fragment
import com.teachernavigator.teachernavigator.application.TeacherNavigatopApp

/**
 *  @author Arthur Korchagin on 20.09.17.
 */


fun Fragment.rootComponent() = (activity.application as TeacherNavigatopApp).getRootComponent()
fun Activity.rootComponent() = (application as TeacherNavigatopApp).getRootComponent()