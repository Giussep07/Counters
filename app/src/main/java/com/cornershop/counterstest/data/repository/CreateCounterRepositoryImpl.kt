package com.cornershop.counterstest.data.repository

import com.cornershop.counterstest.data.remoteSource.createCounter.CreateCounterRemoteDataSource
import com.cornershop.counterstest.domain.repository.CreateCounterRepository
import com.cornershop.counterstest.presentation.state.createCounter.CreateCounterUiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateCounterRepositoryImpl @Inject constructor(private val createCounterRemoteDataSource: CreateCounterRemoteDataSource): CreateCounterRepository {

    override suspend fun createCounter(title: String): Flow<CreateCounterUiState> {
        return createCounterRemoteDataSource.createCounter(title)
    }
}