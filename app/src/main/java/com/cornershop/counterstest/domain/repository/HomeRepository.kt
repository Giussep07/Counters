package com.cornershop.counterstest.domain.repository

import com.cornershop.counterstest.presentation.state.HomeUiState
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getCounters(): Flow<HomeUiState>
}