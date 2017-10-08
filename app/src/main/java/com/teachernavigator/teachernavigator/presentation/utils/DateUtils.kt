package com.teachernavigator.teachernavigator.presentation.utils

import java.util.*

/**
 * Created by lliepmah on 05.10.17
 */

private val SECOND_MILLIS = 1000
private val MINUTE_MILLIS = 60 * SECOND_MILLIS
private val HOUR_MILLIS = 60 * MINUTE_MILLIS
private val DAY_MILLIS = 24 * HOUR_MILLIS


fun Date.getTimeAgo(): String =
        time.getTimeAgo()

fun Long.getTimeAgo(): String {
    var time = this

    if (time < 1000000000000L) {
        // if timestamp given in seconds, convert to millis
        time *= 1000
    }

    val now = System.currentTimeMillis()
    if (time > now || time <= 0) {
        return ""
    }

    // TODO: localize
    val diff = now - time

    return when (diff) {
        in Int.MIN_VALUE..MINUTE_MILLIS -> "только что"
        in MINUTE_MILLIS..2 * MINUTE_MILLIS -> "минуту назад"
        in 2 * MINUTE_MILLIS..50 * MINUTE_MILLIS -> "${diff / MINUTE_MILLIS} минут назад"
        in 50 * MINUTE_MILLIS..90 * MINUTE_MILLIS -> "час назад"
        in 90 * MINUTE_MILLIS..24 * HOUR_MILLIS -> "${diff / HOUR_MILLIS} часов назад"
        in 24 * HOUR_MILLIS..48 * HOUR_MILLIS -> "вчера"
        else -> "${diff / DAY_MILLIS} дней назад"
    }

}