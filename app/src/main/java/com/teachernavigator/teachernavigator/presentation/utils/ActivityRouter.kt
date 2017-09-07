package com.teachernavigator.teachernavigator.presentation.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle

/**
 * Created by root on 24.08.17.
 */
class ActivityRouter {

    companion object {

        fun openActivity(activity: Activity, classType: Class<*>) {
            val intent = Intent(activity, classType)
            activity.startActivity(intent)
        }

        fun openActivity(activity: Activity, bundle: Bundle, classType: Class<*>) {
            val intent = Intent(activity, classType)
            intent.putExtras(bundle)
            activity.startActivity(intent)
        }

        fun openActivityAndClosePrevent(activity: Activity, classType: Class<*>) {
            val intent = Intent(activity, classType)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity.startActivity(intent)
            activity.finish()
        }
    }
}