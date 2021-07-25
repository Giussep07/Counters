package com.cornershop.counterstest.domain.repository

import com.cornershop.counterstest.presentation.state.HomeDecreaseCounterUiState
import com.cornershop.counterstest.presentation.state.HomeIncreaseCounterUiState
import com.cornershop.counterstest.presentation.state.HomeUiState
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getCounters(): Flow<HomeUiState>
    suspend fun decreaseCounter(id: String): Flow<HomeDecreaseCounterUiState>
    suspend fun increaseCounter(id: String): Flow<HomeIncreaseCounterUiState>
}
