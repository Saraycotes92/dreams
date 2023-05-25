package com.cristhianbonilla.oraculo.di

import com.cristhianbonilla.oraculo.data.api.GptApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Singleton
    @Provides
    fun provideGPT3Api(@Named(REMOTE_GPT3_API_BASE_URL) retrofit: Retrofit): GptApi {
        return retrofit.create(GptApi::class.java)
    }
}