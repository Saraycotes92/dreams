package com.cristhianbonilla.oraculo.util

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DispatcherProviderImpl @Inject constructor() : DispatcherProvider {
    override val main get() = Dispatchers.Main
    override val network get() = Dispatchers.IO
}
