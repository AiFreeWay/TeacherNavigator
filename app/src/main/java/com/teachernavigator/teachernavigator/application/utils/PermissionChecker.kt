package com.teachernavigator.teachernavigator.application.utils

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

/**
 * Created by root on 11.08.17.
 */
class PermissionChecker {

    companion object {

        private fun checkPermission(checableActivity: String, requestActivity: Activity, requestCode: Int): Boolean {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(requestActivity, checableActivity) &&
                    PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(requestActivity, checableActivity)) {
                ActivityCompat.requestPermissions(requestActivity, arrayOf(checableActivity), requestCode)
                return false
            }
            return true
        }
    }
}