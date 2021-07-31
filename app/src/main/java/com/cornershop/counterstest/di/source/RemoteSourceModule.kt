package com.cornershop.counterstest.di.source

import com.cornershop.counterstest.data.network.apiService.CountersApi
import com.cornershop.counterstest.data.network.mapper.CounterRemoteMapper
import com.cornershop.counterstest.data.network.remoteSource.CounterRemoteDataSource
import com.cornershop.counterstest.data.network.remoteSource.CounterRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteSourceModule {

    @Singleton
    @Provides
    fun provideCounterRemoteDataSource(countersApi: CountersApi, counterRemoteMapper: CounterRemoteMapper): CounterRemoteDataSource {
        return CounterRemoteDataSourceImpl(countersApi, counterRemoteMapper)
    }

    @Singleton
    @Provides
    fun provideCounterRemoteMapper(): CounterRemoteMapper = CounterRemoteMapper()
}