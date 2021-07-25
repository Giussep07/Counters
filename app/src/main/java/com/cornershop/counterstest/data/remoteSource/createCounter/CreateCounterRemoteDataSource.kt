package com.cornershop.counterstest.data.remoteSource.createCounter

import com.cornershop.counterstest.presentation.state.createCounter.CreateCounterUiState
import kotlinx.coroutines.flow.Flow

interface CreateCounterRemoteDataSource {
    fun createCounter(title: String): Flow<CreateCounterUiState>
}