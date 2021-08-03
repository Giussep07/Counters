package com.cornershop.counterstest.presentation.state.home

import com.cornershop.counterstest.presentation.model.CounterItem

sealed class HomeSearchUiState {
    data class Results(val counters: List<CounterItem>): HomeSearchUiState()
    object NoResults: HomeSearchUiState()
}
