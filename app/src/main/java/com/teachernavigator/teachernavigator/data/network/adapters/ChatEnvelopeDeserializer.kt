package com.teachernavigator.teachernavigator.data.network.adapters

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.teachernavigator.teachernavigator.domain.models.ChatEnvelope
import java.lang.reflect.Type

/**
 * Created by Arthur Korchagin on 28.10.17
 */
object ChatEnvelopeDeserializer : JsonDeserializer<ChatEnvelope> {


    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): ChatEnvelope {

        val jsonObject = json?.asJsonObject

        return when {
            jsonObject?.get("message")?.isJsonObject == true -> context?.deserialize<ChatEnvelope.ChatMessage>(json, ChatEnvelope.ChatMessage::class.java)
            jsonObject?.get("amount_of_members")?.isJsonPrimitive == true -> context?.deserialize<ChatEnvelope.ChatMembers>(json, ChatEnvelope.ChatMembers::class.java)

            else -> ChatEnvelope.ChatError(Error("Unknown message"))

        } ?: throw  Error("Deserialization Error")

    }

}