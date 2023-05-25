package com.cristhianbonilla.oraculo.data

import com.cristhianbonilla.oraculo.data.response.GptResponseEntity


interface GptDatasource {
    suspend fun getDreamMeaning(request: GptRequestEntity): com.cristhianbonilla.oraculo.util.Result<GptResponseEntity>
}