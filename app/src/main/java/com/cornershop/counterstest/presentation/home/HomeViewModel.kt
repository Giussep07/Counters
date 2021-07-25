package com.cornershop.counterstest.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cornershop.counterstest.domain.repository.HomeRepository
import com.cornershop.counterstest.presentation.model.CounterItem
import com.cornershop.counterstest.presentation.state.home.HomeDecreaseCounterUiState
import com.cornershop.counterstest.presentation.state.home.HomeIncreaseCounterUiState
import com.cornershop.counterstest.presentation.state.home.HomeUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    private val _homeUiState = MutableLiveData<HomeUiState>()
    val homeUiState: LiveData<HomeUiState>
        get() = _homeUiState

    private val _homeDecreaseUiState = MutableLiveData<HomeDecreaseCounterUiState>()
    val homeDecreaseUiState: LiveData<HomeDecreaseCounterUiState>
        get() = _homeDecreaseUiState

    private val _homeIncreaseUiState = MutableLiveData<HomeIncreaseCounterUiState>()
    val homeIncreaseUiState: LiveData<HomeIncreaseCounterUiState>
        get() = _homeIncreaseUiState

    fun getCounters() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCounters().collect {
                _homeUiState.postValue(it)
            }
        }
    }

    fun decreaseCounter(counterUiModel: CounterItem.CounterUiModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.decreaseCounter(counterUiModel.id).collect {
                _homeDecreaseUiState.postValue(it)
            }
        }
    }

    fun increaseCounter(counterUiModel: CounterItem.CounterUiModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.increaseCounter(counterUiModel.id).collect {
                _homeIncreaseUiState.postValue(it)
            }
        }
    }
}
