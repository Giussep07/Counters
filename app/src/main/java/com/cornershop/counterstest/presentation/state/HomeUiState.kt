package com.cornershop.counterstest.presentation.state

import com.cornershop.counterstest.domain.model.Counter

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val counters: List<Counter>) : HomeUiState()
    data class Error(val errorMessage: String) : HomeUiState()
}
