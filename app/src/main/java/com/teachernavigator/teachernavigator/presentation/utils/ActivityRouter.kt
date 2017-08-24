package com.teachernavigator.teachernavigator.presentation.utils

import android.app.Activity
import android.content.Intent

/**
 * Created by root on 24.08.17.
 */
class ActivityRouter {

    companion object {

        fun openActivity(activity: Activity, classType: Class<*>) {
            val intent = Intent(activity, classType)
            activity.startActivity(intent)
        }
    }
}