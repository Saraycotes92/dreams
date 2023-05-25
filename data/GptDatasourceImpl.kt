package com.cristhianbonilla.oraculo.data

import com.cristhianbonilla.oraculo.data.api.GptApi
import com.cristhianbonilla.oraculo.util.BaseDataSource
import javax.inject.Inject

class GptDatasourceImpl @Inject constructor(private val gpt3Api: GptApi) :
    BaseDataSource(),
    GptDatasource {


    override suspend fun getDreamMeaning(request: GptRequestEntity) = getResult {
        executeNetworkAction {
            gpt3Api.getDreamFromGpt(request)
        }
    }
}
