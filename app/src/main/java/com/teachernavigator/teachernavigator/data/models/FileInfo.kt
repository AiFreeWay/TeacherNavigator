package com.teachernavigator.teachernavigator.data.models

import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File

/**
 * Created by Arthur Korchagin on 11.10.17
 */
data class FileInfo(private val filePath: String, private val fileMime: String, val fileName: String) {

    fun toRequestBody() =
            RequestBody.create(MediaType.parse(fileMime), File(filePath))

}