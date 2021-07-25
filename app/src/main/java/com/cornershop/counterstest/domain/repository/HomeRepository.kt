package com.cornershop.counterstest.domain.repository

import com.cornershop.counterstest.presentation.state.home.HomeDecreaseCounterUiState
import com.cornershop.counterstest.presentation.state.home.HomeIncreaseCounterUiState
import com.cornershop.counterstest.presentation.state.home.HomeUiState
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getCounters(): Flow<HomeUiState>
    suspend fun decreaseCounter(id: String): Flow<HomeDecreaseCounterUiState>
    suspend fun increaseCounter(id: String): Flow<HomeIncreaseCounterUiState>
}
