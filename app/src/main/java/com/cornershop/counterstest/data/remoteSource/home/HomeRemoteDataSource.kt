package com.cornershop.counterstest.data.remoteSource.home

import com.cornershop.counterstest.presentation.state.HomeUiState
import kotlinx.coroutines.flow.Flow

interface HomeRemoteDataSource {
    fun getCounters(): Flow<HomeUiState>
}