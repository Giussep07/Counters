package com.cornershop.counterstest.domain.repository

import com.cornershop.counterstest.presentation.state.createCounter.CreateCounterUiState
import kotlinx.coroutines.flow.Flow

interface CreateCounterRepository {
    suspend fun createCounter(title: String): Flow<CreateCounterUiState>
}