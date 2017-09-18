package com.teachernavigator.teachernavigator.data.cache

import com.orhanobut.hawk.Hawk

/**
 * Created by root on 23.08.17.
 */
class CacheController {

    companion object {

        val TOKEN_KEY = "token"
        val SETTINGS_KEY = "settings"

        fun putData(key: String, data: Any) {
            Hawk.put(key, data)
        }

        fun <T> getData(key: String, default: T?): T? {
            if (Hawk.contains(key))
                return Hawk.get(key)
            return default
        }
    }
}