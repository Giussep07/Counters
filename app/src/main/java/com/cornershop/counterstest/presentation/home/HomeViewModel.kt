package com.cornershop.counterstest.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cornershop.counterstest.domain.repository.HomeRepository
import com.cornershop.counterstest.presentation.state.HomeUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    private val _homeUiState = MutableLiveData<HomeUiState>()
    val homeUiState: LiveData<HomeUiState>
        get() = _homeUiState

    fun getCounters() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCounters().collect {
                _homeUiState.postValue(it)
            }
        }
    }
}