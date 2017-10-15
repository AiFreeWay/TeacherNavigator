package com.teachernavigator.teachernavigator.data.utils

import android.content.Context
import android.content.SharedPreferences
import com.teachernavigator.teachernavigator.BuildConfig

/**
 * Created by Arthur Korchagin on 15.10.17
 */

val PREFS_FILENAME = "${BuildConfig.APPLICATION_ID}.prefs"

private const val PASSED_POLLS_IDS = "passed_poll_ids"

val Context.prefs: SharedPreferences
    get() = this.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

fun <T> Context.putPrefs(key: String, value: T) =
        prefs.edit()
                .let {
                    when (value) {
                        is Boolean -> it.putBoolean(key, value)
                        is Int -> it.putInt(key, value)
                        is Float -> it.putFloat(key, value)
                        is String -> it.putString(key, value)
                        is Long -> it.putLong(key, value)
                        else -> throw Error("Incompatible Class")
                    }
                }
                .apply()


fun Context.setPollPassed(id: Int) {

    val passedPolls = prefs.getStringSet(PASSED_POLLS_IDS, HashSet<String>())
            .toMutableSet()
            .plus(id.toString())

    prefs.edit()
            .putStringSet(PASSED_POLLS_IDS, passedPolls)
            .apply()
}

fun Context.isPollPassed(id: Int) =
        prefs.getStringSet(PASSED_POLLS_IDS, HashSet<String>())
                .contains(id.toString())