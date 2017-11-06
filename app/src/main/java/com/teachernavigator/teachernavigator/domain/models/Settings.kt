package com.teachernavigator.teachernavigator.domain.models

import com.teachernavigator.teachernavigator.R

/**
 * Created by root on 18.09.17
 */
class Settings {

    var night = false
    var isPushOn = true
    var isSoundOn = true
    var fontType: Int = 2

    val theme: Int
        get() = if (night) {
            when (fontType) {
                0 -> R.style.AppTheme_Night_Least
                1 -> R.style.AppTheme_Night_Lesser
                2 -> R.style.AppTheme_Night_Medium
                3 -> R.style.AppTheme_Night_Larger
                4 -> R.style.AppTheme_Night_Largest
                else -> R.style.AppTheme_Night
            }
        } else {
            when (fontType) {
                0 -> R.style.AppTheme_Day_Least
                1 -> R.style.AppTheme_Day_Lesser
                2 -> R.style.AppTheme_Day_Medium
                3 -> R.style.AppTheme_Day_Larger
                4 -> R.style.AppTheme_Day_Largest
                else -> R.style.AppTheme_Day
            }
        }

}