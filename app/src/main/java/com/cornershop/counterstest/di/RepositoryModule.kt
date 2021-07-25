package com.cornershop.counterstest.di

import com.cornershop.counterstest.data.repository.CreateCounterRepositoryImpl
import com.cornershop.counterstest.data.repository.HomeRepositoryImpl
import com.cornershop.counterstest.domain.repository.CreateCounterRepository
import com.cornershop.counterstest.domain.repository.HomeRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindHomeRemoteRepository(repository: HomeRepositoryImpl): HomeRepository

    @Binds
    abstract fun bindCreateCounterRepository(repository: CreateCounterRepositoryImpl): CreateCounterRepository
}