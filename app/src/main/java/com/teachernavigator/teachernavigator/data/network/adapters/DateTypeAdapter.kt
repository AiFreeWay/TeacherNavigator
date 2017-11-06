package com.teachernavigator.teachernavigator.data.network.adapters

import android.util.Log.d
import com.google.gson.*
import com.google.gson.internal.bind.util.ISO8601Utils
import com.teachernavigator.teachernavigator.presentation.utils.formatDisplayTime
import com.teachernavigator.teachernavigator.presentation.utils.formatServerDatetime
import com.teachernavigator.teachernavigator.presentation.utils.parseServerDatetime
import java.lang.reflect.Type
import java.sql.Timestamp
import java.text.ParseException
import java.text.ParsePosition
import java.util.*

/**
 * Created by Arthur Korchagin on 02.11.17
 */
object DateTypeAdapter : JsonSerializer<Date>, JsonDeserializer<Date> {

    override fun serialize(src: Date, typeOfSrc: Type, context: JsonSerializationContext): JsonPrimitive =
            JsonPrimitive(src.formatServerDatetime)

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Date? {

        if (json !is JsonPrimitive) {
            throw JsonParseException("The date should be a string value")
        }

        return deserializeToDate(json)

    }

    private fun deserializeToDate(json: JsonElement): Date? {
        try {
            val s = "${json.asString} UTC"

            d(javaClass.name, "-> deserializeToDate -> dateString=$s")
            val parseServerDatetime = s.parseServerDatetime
            d(javaClass.name, "-> deserializeToDate -> formattedDate=${parseServerDatetime?.formatDisplayTime}")

            return parseServerDatetime

        } catch (error: Throwable) {
            d(javaClass.name, "-> deserializeToDate -> error=$error")
            error.printStackTrace()
        }

        try {
            return ISO8601Utils.parse(json.asString, ParsePosition(0))
        } catch (e: ParseException) {
            throw JsonSyntaxException(json.asString, e)
        }
    }

}