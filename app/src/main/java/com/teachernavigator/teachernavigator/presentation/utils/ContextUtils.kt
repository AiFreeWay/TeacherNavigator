package com.teachernavigator.teachernavigator.presentation.utils

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore

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