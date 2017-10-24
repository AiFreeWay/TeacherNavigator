package com.teachernavigator.teachernavigator.data.cache

import com.google.firebase.iid.FirebaseInstanceId
import com.orhanobut.hawk.Hawk
import java.util.*

/**
 * Created by root on 23.08.17
 */
class CacheController {

    companion object {

        val USER_KEY = "user"
        val DEVICE_ID_KEY = "device_id"
        val PUSH_ENABLED_KEY = "push_enabled"

        val TOKEN_KEY = "token"
        val SETTINGS_KEY = "settings"

        fun putData(key: String, data: Any) {
            Hawk.put(key, data)
        }

        fun removeData(key: String) {
            if (Hawk.contains(key))
                Hawk.delete(key)
        }

        fun <T> getData(key: String, default: T?): T? = Hawk.get(key, default)

        fun getDeviceId(): String {
            if (!Hawk.contains(DEVICE_ID_KEY)) {
                Hawk.put(DEVICE_ID_KEY, UUID.randomUUID().toString())
            }
            return Hawk.get(DEVICE_ID_KEY)
        }

        fun isPushEnabled(): Boolean {
            if (!Hawk.contains(PUSH_ENABLED_KEY)) {
                Hawk.put(PUSH_ENABLED_KEY, true)
            }
            return Hawk.get(PUSH_ENABLED_KEY)
        }


        fun logout() {
            FirebaseInstanceId.getInstance().deleteInstanceId()
            removeData(TOKEN_KEY)
        }


    }
}