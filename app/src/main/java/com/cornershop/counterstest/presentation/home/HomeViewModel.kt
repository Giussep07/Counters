package com.cornershop.counterstest.presentation.home

import com.cornershop.counterstest.R
import androidx.lifecycle.*
import com.cornershop.counterstest.domain.repository.CounterRepository
import com.cornershop.counterstest.presentation.mapper.CounterPresentationMapper
import com.cornershop.counterstest.presentation.model.CounterItem
import com.cornershop.counterstest.presentation.state.home.*
import com.cornershop.counterstest.presentation.utils.ConnectivityStatusManager
import com.cornershop.counterstest.presentation.utils.ResourcesManager
import com.cornershop.counterstest.presentation.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: CounterRepository,
    private val connectivityStatusManager: ConnectivityStatusManager,
    private val resourcesManager: ResourcesManager,
    private val counterPresentationMapper: CounterPresentationMapper
) : ViewModel() {

    private val _homeUiState = SingleLiveEvent<HomeUiState>()
    val homeUiState: LiveData<HomeUiState>
        get() = _homeUiState

    private val _homeDecreaseUiState = SingleLiveEvent<HomeDecreaseCounterUiState>()
    val homeDecreaseUiState: LiveData<HomeDecreaseCounterUiState>
        get() = _homeDecreaseUiState

    private val _homeIncreaseUiState = SingleLiveEvent<HomeIncreaseCounterUiState>()
    val homeIncreaseUiState: LiveData<HomeIncreaseCounterUiState>
        get() = _homeIncreaseUiState

    private val _homeDeleteUiState = SingleLiveEvent<HomeDeleteCounterUiState>()
    val homeDeleteUiState: LiveData<HomeDeleteCounterUiState>
        get() = _homeDeleteUiState

    private val _homeSearchUiState = SingleLiveEvent<HomeSearchUiState>()
    val homeSearchUiState: LiveData<HomeSearchUiState>
        get() = _homeSearchUiState

    fun getCounters() {
        viewModelScope.launch {
            flow<HomeUiState> {
                emit(HomeUiState.Success(counterPresentationMapper.toUiModel(repository.getCounters())))
            }
                .flowOn(Dispatchers.IO)
                .onStart {
                    emit(HomeUiState.Loading)
                }
                .catch {
                    val errorMessage: String = if (!connectivityStatusManager.hasNetwork()) {
                        resourcesManager.getString(R.string.connection_error_description)
                    } else {
                        resourcesManager.getString(R.string.generic_error_description)
                    }

                    emit(HomeUiState.Error(errorMessage))
                }
                .collect { _homeUiState.postValue(it) }
        }
    }

    fun decreaseCounter(counterUiModel: CounterItem.CounterUiModel) {
        viewModelScope.launch {
            flow<HomeDecreaseCounterUiState> {
                emit(HomeDecreaseCounterUiState.Success(counterPresentationMapper.toUiModel(repository.decreaseCounter(counterUiModel.id))))
            }
                .flowOn(Dispatchers.IO)
                .onStart { emit(HomeDecreaseCounterUiState.Loading) }
                .catch {
                    val errorTitle = resourcesManager.getString(R.string.error_updating_counter_title, counterUiModel.title, counterUiModel.count - 1)
                    val errorDescription: String = if (!connectivityStatusManager.hasNetwork()) {
                        resourcesManager.getString(R.string.connection_error_description)
                    } else {
                        resourcesManager.getString(R.string.generic_error_description)
                    }

                    emit(HomeDecreaseCounterUiState.Error(errorTitle, errorDescription, counterUiModel))
                }
                .collect {
                    _homeDecreaseUiState.postValue(it)
                }
        }
    }

    fun increaseCounter(counterUiModel: CounterItem.CounterUiModel) {
        viewModelScope.launch {
            flow<HomeIncreaseCounterUiState> {
                emit(HomeIncreaseCounterUiState.Success(counterPresentationMapper.toUiModel(repository.increaseCounter(counterUiModel.id))))
            }
                .flowOn(Dispatchers.IO)
                .onStart { emit(HomeIncreaseCounterUiState.Loading) }
                .catch {
                    val errorTitle = resourcesManager.getString(R.string.error_updating_counter_title, counterUiModel.title, (counterUiModel.count + 1))
                    val errorDescription: String = if (!connectivityStatusManager.hasNetwork()) {
                        resourcesManager.getString(R.string.connection_error_description)
                    } else {
                        resourcesManager.getString(R.string.generic_error_description)
                    }

                    emit(HomeIncreaseCounterUiState.Error(errorTitle, errorDescription, counterUiModel))
                }
                .collect {
                    _homeIncreaseUiState.postValue(it)
                }
        }
    }

    fun deleteCounters(countersSelected: List<CounterItem.CounterUiModel>) {
        viewModelScope.launch {
            flow<HomeDeleteCounterUiState> {
                emit(HomeDeleteCounterUiState.Success(counterPresentationMapper.toUiModel(repository.deleteCounters(countersSelected.map { it.id }))))
            }
                .flowOn(Dispatchers.IO)
                .onStart { emit(HomeDeleteCounterUiState.Deleting) }
                .catch {
                    val errorTitle = resourcesManager.getString(R.string.error_deleting_counter_title)

                    val errorDescription: String = if (!connectivityStatusManager.hasNetwork()) {
                        resourcesManager.getString(R.string.connection_error_description)
                    } else {
                        resourcesManager.getString(R.string.generic_error_description)
                    }

                    emit(HomeDeleteCounterUiState.Error(errorTitle, errorDescription, countersSelected))
                }
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
                    emit(HomeSearchUiState.Results(counterPresentationMapper.toUiModel(result)))
                }
            }
                .flowOn(Dispatchers.IO)
                .collect {
                    _homeSearchUiState.postValue(it)
                }
        }
    }
}
