package com.cornershop.counterstest.di

import com.cornershop.counterstest.data.repository.CounterRepositoryImpl
import com.cornershop.counterstest.domain.repository.CounterRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindHomeRemoteRepository(repository: CounterRepositoryImpl): CounterRepository
}