package com.cornershop.counterstest.presentation.state.home

import com.cornershop.counterstest.presentation.model.CounterItem

sealed class HomeIncreaseCounterUiState {
    object Loading : HomeIncreaseCounterUiState()
    data class Success(val counters: List<CounterItem>) : HomeIncreaseCounterUiState()
    data class Error(val errorTitle: String, val errorDescription: String, val counter: CounterItem.CounterUiModel) : HomeIncreaseCounterUiState()
}
