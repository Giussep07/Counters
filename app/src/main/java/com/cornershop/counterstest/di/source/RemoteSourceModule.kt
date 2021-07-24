package com.cornershop.counterstest.di.source

import com.cornershop.counterstest.data.apiService.CountersApi
import com.cornershop.counterstest.data.mapper.CounterRemoteMapper
import com.cornershop.counterstest.data.remoteSource.home.HomeRemoteDataSource
import com.cornershop.counterstest.data.remoteSource.home.HomeRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteSourceModule {

    @Singleton
    @Provides
    fun provideHomeRemoteDataSource(countersApi: CountersApi, counterRemoteMapper: CounterRemoteMapper): HomeRemoteDataSource {
        return HomeRemoteDataSourceImpl(countersApi, counterRemoteMapper)
    }

    @Singleton
    @Provides
    fun provideCounterRemoteMapper(): CounterRemoteMapper = CounterRemoteMapper()
}