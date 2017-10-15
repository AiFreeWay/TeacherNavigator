package com.teachernavigator.teachernavigator.presentation.utils

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.widget.Toast
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseView

/**
 * Created by lliepmah on 03.10.17
 */

fun Context.getPathFromUri(contentUri: Uri): String {
    var cursor: Cursor? = null
    try {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        cursor = contentResolver.query(contentUri, projection, null, null, null)
        val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(columnIndex)
    } finally {
        if (cursor != null) {
            cursor.close()
        }
    }
}

fun BaseView.notImplemented() =
        Toast.makeText(getContext(), R.string.not_implemented, Toast.LENGTH_LONG).show()


inline fun <reified T> Fragment.arg(key: String): T =
        arguments.get(key) as T

inline fun <reified T> Fragment.argNullable(key: String): T? =
        arguments?.get(key) as? T