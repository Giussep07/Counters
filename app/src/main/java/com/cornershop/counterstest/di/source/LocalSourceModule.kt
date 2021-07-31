package com.cornershop.counterstest.di.source

import com.cornershop.counterstest.data.database.CounterDao
import com.cornershop.counterstest.data.database.localSource.CounterLocalDataSource
import com.cornershop.counterstest.data.database.localSource.CounterLocalDataSourceImpl
import com.cornershop.counterstest.data.database.mapper.CounterLocalMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalSourceModule {

    @Singleton
    @Provides
    fun provideCounterLocalDataSource(counterDao: CounterDao): CounterLocalDataSource {
        return CounterLocalDataSourceImpl(counterDao)
    }

    @Singleton
    @Provides
    fun provideCounterLocalMapper() = CounterLocalMapper()
}