package com.cornershop.counterstest.presentation.state.createCounter

import com.cornershop.counterstest.domain.model.Counter

sealed class CreateCounterUiState {
    object Creating: CreateCounterUiState()
    data class Success(val counters: List<Counter>) : CreateCounterUiState()
    data class Error(val errorTitle: String, val errorMessage: String, val counterTitle: String): CreateCounterUiState()
}
