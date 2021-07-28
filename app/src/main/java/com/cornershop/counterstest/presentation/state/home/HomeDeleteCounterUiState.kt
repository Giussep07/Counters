package com.cornershop.counterstest.presentation.state.home

import com.cornershop.counterstest.domain.model.Counter

sealed class HomeDeleteCounterUiState {
    object Deleting : HomeDeleteCounterUiState()
    data class Success(val counters: List<Counter>) : HomeDeleteCounterUiState()
    data class Error(val errorMessage: String) : HomeDeleteCounterUiState()
}
