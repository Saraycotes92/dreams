package com.cristhianbonilla.oraculo.domain

data class GptRequest(
    val model: String,
    val messages: List<MessageRequest>
)

data class MessageRequest(
    val role: String,
    val content: String
)