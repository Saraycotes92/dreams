package com.cristhianbonilla.oraculo.data

import com.cristhianbonilla.oraculo.data.response.toModel
import com.cristhianbonilla.oraculo.domain.GptRepository
import com.cristhianbonilla.oraculo.domain.GptRequest
import com.cristhianbonilla.oraculo.util.GenericErrorMapper
import com.cristhianbonilla.oraculo.util.ResultDomain
import com.cristhianbonilla.oraculo.util.baseResponseErrorHandler
import javax.inject.Inject

class GptRepositoryImpl @Inject constructor(private val dataSource: GptDatasource) : GptRepository {
    override suspend fun getDreamMeaningFromGpt(gptRequest: GptRequest)=
        baseResponseErrorHandler(
            GenericErrorMapper,
            dataSource.getDreamMeaning(gptRequest.toEntity())
        ) { ResultDomain.Success(it.toModel()) }
}