package com.cristhianbonilla.oraculo.util

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.lang.AssertionError
import java.net.HttpURLConnection
import javax.inject.Inject


class PrivateInterceptor @Inject constructor(
    private val session: Session,
    private val networkStatus: NetworkStatus
) : Interceptor {

    companion object {
        private const val HEADER_NAME_TOKEN = "Authorization"
        private const val HEADER_NAME_ACCEPT = "Accept"
        private const val HEADER_NAME_CONTENT = "Content-Type"
        private const val HEADER_VALUE_JSON = "application/json; charset=utf-8"
        private const val HEADER_NAME_PROGRESSID = "progressId"
        private const val TOKEN_NOT_SEND = 701
        private const val TOKEN_EXPIRED = 302
        private const val ATHORIZATION_HEADER_NOT_FOUND = 301
    }

    @Throws(NetworkException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!networkStatus.isConnected()) {
            throw NetworkException
        }
        val request = chain.request()
        val requestBuilder = request.newBuilder()
        if (session.getAccessToken() != "") {
            requestBuilder
                .addHeader("Authorization", "Bearer " + session.getAccessToken())
        } else {
            try {
                requestBuilder
                    .addHeader("Authorization", "Bearer " + session.getRefreshToken())
            } catch (e: AssertionError) {
                Log.d("Cristhian", "no tiene token")
            }
        }

        Log.d("Cristhian", session.getAccessToken())

        var response = chain.proceed(requestBuilder.build())
        when (response.code) {
            HttpURLConnection.HTTP_OK -> {
                // TODO Invalidate caches
//                localHandler.invalidateCaches()
            }
            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                //     GlobalScope.launch(IO) { session.logout() }
            }
            HttpURLConnection.HTTP_FORBIDDEN -> {
            }
            HttpURLConnection.HTTP_PRECON_FAILED -> {
                // TODO One session for channel
            }
            TOKEN_NOT_SEND -> {
                // GlobalScope.launch(IO) { session.logout() }
            }

            TOKEN_EXPIRED -> {
                //   GlobalScope.launch(IO) { session.logout() }
            }
            ATHORIZATION_HEADER_NOT_FOUND -> {
            }
        }
        return response
    }
}
