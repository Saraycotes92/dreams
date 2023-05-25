package com.cristhianbonilla.oraculo.domain

import com.cristhianbonilla.oraculo.util.ResultDomain

interface GptRepository {
    suspend fun getDreamMeaningFromGpt(gptRequest: GptRequest): ResultDomain<GptResponseModel>
}