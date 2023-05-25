package com.cristhianbonilla.oraculo.util

object GenericErrorMapper : ErrorMapper() {
    override fun customError(code: Int?, errorBody: String) = ResultDomain.Error(GenericError)
    override fun genericError() = GenericError
}

object GenericError : ErrorDomain()
