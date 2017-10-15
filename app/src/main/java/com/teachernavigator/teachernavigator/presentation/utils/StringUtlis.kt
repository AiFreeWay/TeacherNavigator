package com.teachernavigator.teachernavigator.presentation.utils

/**
 * Created by Arthur Korchagin on 15.10.17
 */

fun String?.makeLess(length: Int) =
        this?.let { if (it.length < length) it else "${it.take(length)}..." }