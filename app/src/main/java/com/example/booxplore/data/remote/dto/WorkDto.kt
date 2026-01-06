package com.example.booxplore.data.remote.dto

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable
data class WorkDto(
    val title: String,
    @Serializable(with = DescriptionSerializer::class)
    val description: String? = null,
    @SerialName("covers")
    val covers: List<Int>? = null,
    @SerialName("number_of_pages")
    val numberOfPages: Int? = null
)

object DescriptionSerializer : KSerializer<String?> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Description", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): String? {
        val jsonInput = decoder as? kotlinx.serialization.json.JsonDecoder ?: return null
        val element = jsonInput.decodeJsonElement()

        return when {
            element is JsonPrimitive && element.isString -> element.content
            element is JsonObject -> element["value"]?.jsonPrimitive?.content
            else -> null
        }
    }

    override fun serialize(encoder: Encoder, value: String?) {
        if (value != null) {
            encoder.encodeString(value)
        } else {
            encoder.encodeNull()
        }
    }
}
