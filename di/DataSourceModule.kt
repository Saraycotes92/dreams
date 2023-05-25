package com.cristhianbonilla.oraculo.di

import com.cristhianbonilla.oraculo.data.GptDatasource
import com.cristhianbonilla.oraculo.data.GptDatasourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Singleton
    @Binds
    abstract fun provideGpt3DataSource(impl: GptDatasourceImpl): GptDatasource
}