package com.cornershop.counterstest.presentation.state.home

import com.cornershop.counterstest.domain.model.Counter

sealed class HomeDecreaseCounterUiState {
    object Loading : HomeDecreaseCounterUiState()
    data class Success(val counters: List<Counter>) : HomeDecreaseCounterUiState()
    data class Error(val errorMessage: String) : HomeDecreaseCounterUiState()
}
