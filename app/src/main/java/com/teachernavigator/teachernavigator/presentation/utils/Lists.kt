package com.teachernavigator.teachernavigator.presentation.utils

/**
 * Created by Arthur Korchagin on 15.10.17
 */

public inline fun List<*>?.isNullOrEmpty(): Boolean = this == null || this.isEmpty()