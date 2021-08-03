package com.cornershop.counterstest.presentation.state.home

import com.cornershop.counterstest.presentation.model.CounterItem

sealed class HomeDecreaseCounterUiState {
    object Loading : HomeDecreaseCounterUiState()
    data class Success(val counters: List<CounterItem>) : HomeDecreaseCounterUiState()
    data class Error(val errorTitle: String, val errorDescription: String, val counter: CounterItem.CounterUiModel) : HomeDecreaseCounterUiState()
}
