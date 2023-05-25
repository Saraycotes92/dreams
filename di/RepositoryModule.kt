package com.cristhianbonilla.oraculo.di

import com.cristhianbonilla.oraculo.data.GptRepositoryImpl
import com.cristhianbonilla.oraculo.domain.GptRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun provideGptRepository(impl: GptRepositoryImpl): GptRepository
}