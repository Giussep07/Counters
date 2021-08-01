package com.cornershop.counterstest.presentation.home

import androidx.lifecycle.*
import com.cornershop.counterstest.domain.repository.CounterRepository
import com.cornershop.counterstest.presentation.model.CounterItem
import com.cornershop.counterstest.presentation.state.home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: CounterRepository) : ViewModel() {

    private val _homeUiState = MutableLiveData<HomeUiState>()
    val homeUiState: LiveData<HomeUiState>
        get() = _homeUiState

    private val _homeDecreaseUiState = MutableLiveData<HomeDecreaseCounterUiState>()
    val homeDecreaseUiState: LiveData<HomeDecreaseCounterUiState>
        get() = _homeDecreaseUiState

    private val _homeIncreaseUiState = MutableLiveData<HomeIncreaseCounterUiState>()
    val homeIncreaseUiState: LiveData<HomeIncreaseCounterUiState>
        get() = _homeIncreaseUiState

    private val _homeDeleteUiState = MutableLiveData<HomeDeleteCounterUiState>()
    val homeDeleteUiState: LiveData<HomeDeleteCounterUiState>
        get() = _homeDeleteUiState

    private val _homeSearchUiState = MutableLiveData<HomeSearchUiState>()
    val homeSearchUiState: LiveData<HomeSearchUiState>
        get() = _homeSearchUiState

    fun getCounters() {
        viewModelScope.launch {
            flow<HomeUiState> {
                emit(HomeUiState.Success(repository.getCounters()))
            }
                .flowOn(Dispatchers.IO)
                .onStart { emit(HomeUiState.Loading) }
                .catch { emit(HomeUiState.Error(it.message.toString())) }
                .collect { _homeUiState.postValue(it) }
        }
    }

    fun decreaseCounter(counterUiModel: CounterItem.CounterUiModel) {
        viewModelScope.launch {
            flow<HomeDecreaseCounterUiState> {
                emit(HomeDecreaseCounterUiState.Success(repository.decreaseCounter(counterUiModel.id)))
            }
                .flowOn(Dispatchers.IO)
                .onStart { emit(HomeDecreaseCounterUiState.Loading) }
                .catch { emit(HomeDecreaseCounterUiState.Error(it.message.toString())) }
                .collect {
                    _homeDecreaseUiState.postValue(it)
                }
        }
    }

    fun increaseCounter(counterUiModel: CounterItem.CounterUiModel) {
        viewModelScope.launch {
            flow<HomeIncreaseCounterUiState> {
                emit(HomeIncreaseCounterUiState.Success(repository.increaseCounter(counterUiModel.id)))
            }
                .flowOn(Dispatchers.IO)
                .onStart { emit(HomeIncreaseCounterUiState.Loading) }
                .catch { emit(HomeIncreaseCounterUiState.Error(it.message.toString())) }
                .collect {
                    _homeIncreaseUiState.postValue(it)
                }
        }
    }

    fun deleteCounters(countersSelected: List<CounterItem.CounterUiModel>) {
        viewModelScope.launch {
            flow<HomeDeleteCounterUiState> {
                emit(HomeDeleteCounterUiState.Success(repository.deleteCounters(countersSelected.map { it.id })))
            }
                .flowOn(Dispatchers.IO)
                .onStart { emit(HomeDeleteCounterUiState.Deleting) }
                .catch { emit(HomeDeleteCounterUiState.Error(it.message.toString())) }
                .collect {
                    _homeDeleteUiState.postValue(it)
                }
        }
    }

    fun searchCounter(query: String) {
        viewModelScope.launch {
            flow<HomeSearchUiState> {
                val result = repository.searchCounter(query)

                if (result.isEmpty()) {
                    emit(HomeSearchUiState.NoResults)
                } else {
                    emit(HomeSearchUiState.Results(result))
                }
            }
                .flowOn(Dispatchers.IO)
                .collect {
                _homeSearchUiState.postValue(it)
            }
        }
    }
}
