package com.cristhianbonilla.oraculo.util

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    val main: CoroutineDispatcher

    val network: CoroutineDispatcher
}
