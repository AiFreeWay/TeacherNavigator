package com.teachernavigator.teachernavigator.presentation.models

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import com.teachernavigator.teachernavigator.R

/**
 * Created by lliepmah on 08.10.17
 */
enum class Info(val pathId: Int,
                @StringRes val title: Int = 0,
                @DrawableRes val icon: Int = 0,
                val visible: Boolean = true) {

    LEGAL(0, R.string.label_legal, R.drawable.ic_legal),
    INTERNET(1, R.string.label_internet_friend, R.drawable.ic_internet),
    CARE(2, R.string.label_care, R.drawable.ic_donation),
    FINANCE(5, R.string.label_finance, R.drawable.ic_finance),
    COURSES(3, R.string.label_courses, R.drawable.ic_courses),
    ADVISE(4, 0, 0, false),

}