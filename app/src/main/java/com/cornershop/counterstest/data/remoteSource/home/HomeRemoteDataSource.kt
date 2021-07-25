package com.cornershop.counterstest.data.remoteSource.home

import com.cornershop.counterstest.presentation.state.HomeDecreaseCounterUiState
import com.cornershop.counterstest.presentation.state.HomeIncreaseCounterUiState
import com.cornershop.counterstest.presentation.state.HomeUiState
import kotlinx.coroutines.flow.Flow

interface HomeRemoteDataSource {
    fun getCounters(): Flow<HomeUiState>
    fun decreaseCounter(id: String): Flow<HomeDecreaseCounterUiState>
    fun increaseCounter(id: String): Flow<HomeIncreaseCounterUiState>
}
