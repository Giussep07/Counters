package com.cornershop.counterstest.presentation.state.home

import com.cornershop.counterstest.domain.model.Counter

sealed class HomeSearchUiState {
    data class Results(val counters: List<Counter>): HomeSearchUiState()
    object NoResults: HomeSearchUiState()
}
