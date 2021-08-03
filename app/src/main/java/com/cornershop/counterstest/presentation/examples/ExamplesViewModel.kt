package com.cornershop.counterstest.presentation.examples

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cornershop.counterstest.R
import com.cornershop.counterstest.domain.repository.CounterRepository
import com.cornershop.counterstest.presentation.state.createCounter.CreateCounterUiState
import com.cornershop.counterstest.presentation.utils.ConnectivityStatusManager
import com.cornershop.counterstest.presentation.utils.ResourcesManager
import com.cornershop.counterstest.presentation.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExamplesViewModel @Inject constructor(private val repository: CounterRepository,
    private val connectivityStatusManager: ConnectivityStatusManager,
    private val resourcesManager: ResourcesManager) : ViewModel() {

    private val _createCounterUiState = SingleLiveEvent<CreateCounterUiState>()
    val createCounterUiState: LiveData<CreateCounterUiState>
        get() = _createCounterUiState

    fun createCounter(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            flow<CreateCounterUiState> {
                emit(CreateCounterUiState.Success(repository.createCounter(title)))
            }
                .flowOn(Dispatchers.IO)
                .onStart { emit(CreateCounterUiState.Creating) }
                .catch {
                    val errorTitle = resourcesManager.getString(R.string.error_creating_counter_title)
                    val errorDescription: String = if (!connectivityStatusManager.hasNetwork()) {
                        resourcesManager.getString(R.string.connection_error_description)
                    } else {
                        resourcesManager.getString(R.string.generic_error_description)
                    }

                    emit(CreateCounterUiState.Error(errorTitle, errorDescription, title))
                }
                .collect {
                    _createCounterUiState.postValue(it)
                }
        }
    }
}