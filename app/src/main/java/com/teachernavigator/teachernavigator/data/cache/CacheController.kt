package com.teachernavigator.teachernavigator.data.cache

import com.google.firebase.iid.FirebaseInstanceId
import com.orhanobut.hawk.Hawk
import com.teachernavigator.teachernavigator.domain.models.Settings
import io.reactivex.Completable
import java.util.UUID

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

        fun isPushEnabled() =
            Hawk.get<Settings>(SETTINGS_KEY)?.isPushOn == true

        fun logout(): Completable = Completable.fromAction {
            FirebaseInstanceId.getInstance()
                .deleteInstanceId()
            removeData(TOKEN_KEY)
        }

    }
}