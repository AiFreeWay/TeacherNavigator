package com.teachernavigator.teachernavigator.data.network.adapters

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.teachernavigator.teachernavigator.domain.models.Author
import java.lang.reflect.Type

/**
 * Created by lliepmah on 05.10.17
 */
class UserDeserializer(private val pureGson: Gson) : JsonDeserializer<Author> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Author =
            if (json?.isJsonObject == true) {
                pureGson.fromJson(json, typeOfT)
            } else {
                Author(id = json?.asInt ?: 0)
            }
}