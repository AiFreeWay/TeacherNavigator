package com.teachernavigator.teachernavigator.presentation.utils

import android.content.Context
import com.google.gson.internal.bind.util.ISO8601Utils
import com.teachernavigator.teachernavigator.R
import java.text.ParseException
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.atomic.AtomicReference

/**
 * Created by lliepmah on 05.10.17
 */

private val SECOND_MILLIS = 1000
private val MINUTE_MILLIS = 60 * SECOND_MILLIS
private val HOUR_MILLIS = 60 * MINUTE_MILLIS
private val DAY_MILLIS = 24 * HOUR_MILLIS

/***************************************************************2017-06-13T01:41:31+03:00 */
const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z' Z"
const val SERVER_DATE = "dd.MM.yy"

private val DISPLAY_DATE_FORMAT = AtomicReference(SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()))
private val DISPLAY_TIME_FORMAT = AtomicReference(SimpleDateFormat("HH:mm", Locale.getDefault()))
private val DISPLAY_DATETIME_FORMAT = AtomicReference(SimpleDateFormat("dd.MM.yyyy в HH:mm", Locale.getDefault()))

private val SERVER_DATETIME_FORMAT = AtomicReference(SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.getDefault()))
private val SERVER_DATE_FORMAT = AtomicReference(SimpleDateFormat(SERVER_DATE, Locale.getDefault()))

val Date.formatDisplayTime: String?
    get() = DISPLAY_TIME_FORMAT.get().format(this)
val Date.formatDisplayDateTime: String
    get() = DISPLAY_DATETIME_FORMAT.get().format(this)
val Date.formatServerDate: String?
    get() = SERVER_DATE_FORMAT.get().format(this)
val Date.formatDisplayDate: String
    get() = DISPLAY_DATE_FORMAT.get().format(this)
val Date.formatServerDatetime: String?
    get() = SERVER_DATETIME_FORMAT.get().format(this)

val String.parseServerDate: Date?
    get() {

        try {
            return SERVER_DATE_FORMAT.get().parse(this)
        } catch (ex: ParseException) {
            //
        }

        try {
            return ISO8601Utils.parse(this, ParsePosition(0))
        } catch (ex: ParseException) {
            //
        }

        return null
    }


val String.parseServerDatetime: Date?
    get() {

        try {
            return SERVER_DATETIME_FORMAT.get().parse(this)
        } catch (ex: ParseException) {
            //
        }

        try {
            return ISO8601Utils.parse(this, ParsePosition(0))
        } catch (ex: ParseException) {
            //
        }

        return null
    }

fun Date.getTimeAgo(context: Context): String =
        time.getTimeAgo(context)

fun Long.getTimeAgo(context: Context): String {
    var time = this

    if (time < 1000000000000L) {
        time *= 1000
    }

    val now = System.currentTimeMillis()

    val diff = now - time
    return when (diff) {
        in Int.MIN_VALUE..MINUTE_MILLIS -> context.getString(R.string.just_now)
        in MINUTE_MILLIS..2 * MINUTE_MILLIS -> context.getString(R.string.minute_ago)
        in 2 * MINUTE_MILLIS..50 * MINUTE_MILLIS -> (diff / MINUTE_MILLIS).let { context.resources.getQuantityString(R.plurals.minutes_ago, it.toInt(), it) }
        in 50 * MINUTE_MILLIS..90 * MINUTE_MILLIS -> context.getString(R.string.hour_ago)
        in 90 * MINUTE_MILLIS..24 * HOUR_MILLIS -> (diff / HOUR_MILLIS).let { context.resources.getQuantityString(R.plurals.hours_ago, it.toInt(), it) }
        in 24 * HOUR_MILLIS..48 * HOUR_MILLIS -> context.getString(R.string.yesterday)
        else -> (diff / DAY_MILLIS).let { context.resources.getQuantityString(R.plurals.days_ago, it.toInt(), it) }
    }

}