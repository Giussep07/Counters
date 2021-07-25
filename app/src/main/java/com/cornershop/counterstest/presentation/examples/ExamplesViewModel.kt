package com.cornershop.counterstest.presentation.examples

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cornershop.counterstest.domain.repository.CreateCounterRepository
import com.cornershop.counterstest.presentation.state.createCounter.CreateCounterUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExamplesViewModel @Inject constructor(private val repository: CreateCounterRepository) : ViewModel() {

    private val _createCounterUiState = MutableLiveData<CreateCounterUiState>()
    val createCounterUiState: LiveData<CreateCounterUiState>
        get() = _createCounterUiState

    fun createCounter(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.createCounter(title).collect {
                _createCounterUiState.postValue(it)
            }
        }
    }
}