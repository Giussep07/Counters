package com.cornershop.counterstest.presentation.state.home

import com.cornershop.counterstest.presentation.model.CounterItem

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val counters: List<CounterItem>) : HomeUiState()
    data class Error(val errorMessage: String) : HomeUiState()
}
