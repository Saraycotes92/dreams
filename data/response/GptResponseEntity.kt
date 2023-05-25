package com.cristhianbonilla.oraculo.data.response

import com.cristhianbonilla.oraculo.domain.Choice
import com.cristhianbonilla.oraculo.domain.GptResponseModel
import com.cristhianbonilla.oraculo.domain.Message
import com.cristhianbonilla.oraculo.domain.Usage
import com.google.gson.annotations.SerializedName

data class GptResponseEntity(
    @SerializedName("id")
    val id: String,

    @SerializedName("object")
    val `object`: String,

    @SerializedName("created")
    val created: Long,

    @SerializedName("choices")
    val choices: List<ChoiceEntity>,

    @SerializedName("usage")
    val usage: UsageEntity
)

data class ChoiceEntity(
    @SerializedName("index")
    val index: Int,

    @SerializedName("message")
    val message: MessageEntity,

    @SerializedName("finish_reason")
    val finishReason: String
)

data class MessageEntity(
    @SerializedName("role")
    val role: String,

    @SerializedName("content")
    val content: String
)

data class UsageEntity(
    @SerializedName("prompt_tokens")
    val promptTokens: Int,

    @SerializedName("completion_tokens")
    val completionTokens: Int,

    @SerializedName("total_tokens")
    val totalTokens: Int
)

fun GptResponseEntity.toModel() = GptResponseModel(
    id = this.id,
    `object` = this.`object`,
    created = this.created,
    choices = this.choices.map { it.toModel() },
    usage = this.usage.toModel()
)

fun ChoiceEntity.toModel() = Choice(
    index = this.index,
    message = this.message.toModel(),
    finish_reason = this.finishReason
)

fun MessageEntity.toModel() = Message(
    role = this.role,
    content = this.content
)

fun UsageEntity.toModel() = Usage(
    prompt_tokens = this.promptTokens,
    completion_tokens = this.completionTokens,
    total_tokens = this.totalTokens
)
