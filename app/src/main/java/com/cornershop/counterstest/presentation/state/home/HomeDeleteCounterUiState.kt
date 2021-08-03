package com.cornershop.counterstest.presentation.state.home

import com.cornershop.counterstest.presentation.model.CounterItem

sealed class HomeDeleteCounterUiState {
    object Deleting : HomeDeleteCounterUiState()
    data class Success(val counters: List<CounterItem>) : HomeDeleteCounterUiState()
    data class Error(val errorTitle: String, val errorMessage: String, val counters: List<CounterItem.CounterUiModel>) : HomeDeleteCounterUiState()
}
