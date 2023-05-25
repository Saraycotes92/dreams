package com.cristhianbonilla.oraculo.di
import com.cristhianbonilla.oraculo.BuildConfig
import com.cristhianbonilla.oraculo.util.NetworkInterceptor
import com.cristhianbonilla.oraculo.util.PrivateInterceptor
import com.cristhianbonilla.oraculo.util.md5
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.crypto.Cipher.PRIVATE_KEY
import javax.inject.Named
import javax.inject.Singleton

private const val TIME_STAMP_KEY = "ts"
private const val HASH_KEY = "hash"
private const val CONTENT_TYPE_KEY = "Content-Type"
private const val CONTENT_TYPE = "application/json"
private const val HEADER_ACCEPT_KEY = "Accept"
private const val HEADER_ACCEPT = "application/json"
private const val TIMEOUT = 90L
private const val DIVIDER_TIMESTAMP = 100
private const val TS = ""
const val REMOTE_GPT3_API = "remoteGpt3Api"
const val REMOTE_API = "remoteApi"
const val REMOTE_GPT3_API_BASE_URL = "gpt3BaseUrl"
const val BASE_PATH_URL = "baseUrl"
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    @Named(BASE_PATH_URL)
    fun provideRetrofit(@Named(REMOTE_API) httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }

    @Provides
    @Singleton
    @Named(REMOTE_GPT3_API_BASE_URL)
    fun provideRetrofitGPT3(@Named(REMOTE_GPT3_API) httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.GPT3_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }

    @Provides
    @Singleton
    @Named(REMOTE_API)
    fun provideOkHttpClient(privateInterceptor: PrivateInterceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()

        httpClient.connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(NetworkInterceptor.httpLogging())
            .addInterceptor(privateInterceptor)
        httpClient.addInterceptor { chain ->
            val ts = "1"
            val hash = ts.plus(PRIVATE_KEY)
            val original = chain.request()
            val originalHttpUrl =
                original.url.newBuilder().addQueryParameter(TIME_STAMP_KEY, ts)
                    .addQueryParameter(HASH_KEY, hash.md5()).build()

            val requestBuilder = original.newBuilder().url(originalHttpUrl)
            requestBuilder.header(CONTENT_TYPE_KEY, CONTENT_TYPE)
            requestBuilder.header(HEADER_ACCEPT_KEY, HEADER_ACCEPT)
            // requestBuilder.addHeader("Authorization", "Bearer $PRIVATE_KEY")

            val request = requestBuilder.build()
            chain.proceed(request)
        }

        return httpClient.build()
    }

    @Provides
    @Singleton
    @Named(REMOTE_GPT3_API)
    fun provideGpt3HttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()

        httpClient.connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(NetworkInterceptor.httpLogging())
        httpClient.addInterceptor { chain ->
            val ts = "1"
            val hash = ts.plus(PRIVATE_KEY)
            val original = chain.request()
            val originalHttpUrl =
                original.url.newBuilder().addQueryParameter(TIME_STAMP_KEY, ts)
                    .addQueryParameter(HASH_KEY, hash.md5()).build()

            val requestBuilder = original.newBuilder().url(originalHttpUrl)
            requestBuilder.header(CONTENT_TYPE_KEY, CONTENT_TYPE)
            requestBuilder.header(HEADER_ACCEPT_KEY, HEADER_ACCEPT)
            requestBuilder.addHeader("Authorization", "Bearer ${BuildConfig.PRIVATE_KEY}")

            val request = requestBuilder.build()
            chain.proceed(request)
        }

        return httpClient.build()
    }
}
