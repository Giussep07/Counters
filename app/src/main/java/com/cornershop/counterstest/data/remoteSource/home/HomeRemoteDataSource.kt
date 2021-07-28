package com.cornershop.counterstest.data.remoteSource.home

import com.cornershop.counterstest.presentation.state.home.HomeDecreaseCounterUiState
import com.cornershop.counterstest.presentation.state.home.HomeDeleteCounterUiState
import com.cornershop.counterstest.presentation.state.home.HomeIncreaseCounterUiState
import com.cornershop.counterstest.presentation.state.home.HomeUiState
import kotlinx.coroutines.flow.Flow

interface HomeRemoteDataSource {
    fun getCounters(): Flow<HomeUiState>
    fun decreaseCounter(id: String): Flow<HomeDecreaseCounterUiState>
    fun increaseCounter(id: String): Flow<HomeIncreaseCounterUiState>
    fun deleteCounters(ids: List<String>): Flow<HomeDeleteCounterUiState>
}
