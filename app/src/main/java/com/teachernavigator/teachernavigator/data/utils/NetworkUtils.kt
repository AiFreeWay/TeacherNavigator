package com.teachernavigator.teachernavigator.data.utils

import okhttp3.MediaType
import okhttp3.RequestBody

/**
 * Created by Arthur Korchagin on 11.10.17
 */

//val char = '"'
//val tagsString = "[$char${TextUtils.join("$char,$char", tags)}$char]"
//
//val tagsRequest = RequestBody.create(MediaType.parse("application/json"), tagsString)
//val titleRequest = RequestBody.create(MediaType.parse("application/json"), title)
//val textRequest = RequestBody.create(MediaType.parse("application/json"), text)

fun String.toRequestBody(mime: String = "application/json") = RequestBody.create(MediaType.parse(mime), this)

fun <E> List<E>.toRequestBody(mime: String = "application/json")
        = RequestBody.create(MediaType.parse(mime), joinToString("\",\"", "[\"", "\"]"))