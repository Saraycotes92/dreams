package com.cristhianbonilla.oraculo.util

abstract class ErrorMapper {

    abstract fun customError(code: Int?, errorBody: String): ResultDomain.Error

    abstract fun genericError(): ErrorDomain
}
