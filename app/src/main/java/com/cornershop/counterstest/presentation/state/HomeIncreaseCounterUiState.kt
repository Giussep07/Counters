package com.cornershop.counterstest.presentation.state

import com.cornershop.counterstest.domain.model.Counter

sealed class HomeIncreaseCounterUiState {
    object Loading : HomeIncreaseCounterUiState()
    data class Success(val counters: List<Counter>) : HomeIncreaseCounterUiState()
    data class Error(val errorMessage: String) : HomeIncreaseCounterUiState()
}
