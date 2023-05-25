package com.cristhianbonilla.oraculo.data

import com.cristhianbonilla.oraculo.domain.GptRequest
import com.cristhianbonilla.oraculo.domain.MessageRequest
import com.google.gson.annotations.SerializedName

data class GptRequestEntity(
    @SerializedName("model")
    val model: String,
    @SerializedName("messages")
    val messages: List<MessageRequestEntity>
)

data class MessageRequestEntity(
    @SerializedName("role")
    val role: String,
    @SerializedName("content")
    val content: String
)

fun GptRequest.toEntity() = GptRequestEntity(model, messages.map { it.toEntity() })

fun MessageRequest.toEntity() = MessageRequestEntity(role, content)