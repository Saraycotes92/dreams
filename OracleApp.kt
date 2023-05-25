package com.cristhianbonilla.oraculo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OracleApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
