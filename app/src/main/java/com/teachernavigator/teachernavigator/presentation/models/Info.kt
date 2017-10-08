package com.teachernavigator.teachernavigator.presentation.models

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import com.teachernavigator.teachernavigator.R

/**
 * Created by lliepmah on 08.10.17
 */
enum class Info(@StringRes val title: Int,
                @DrawableRes val icon: Int) {

    LEGAL(R.string.label_legal, R.drawable.ic_legal),
    INTERNET(R.string.label_internet_friend, R.drawable.ic_internet),
    CARE(R.string.label_care, R.drawable.ic_donation),
    COURSES(R.string.label_courses, R.drawable.ic_courses)

}