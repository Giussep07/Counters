package com.cornershop.counterstest.data.remoteSource.createCounter

import com.cornershop.counterstest.data.apiService.CountersApi
import com.cornershop.counterstest.data.mapper.CounterRemoteMapper
import com.cornershop.counterstest.data.model.request.CreateCounterRequestModel
import com.cornershop.counterstest.presentation.state.createCounter.CreateCounterUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateCounterRemoteDataSourceImpl @Inject constructor(private val countersApi: CountersApi,
        private val counterRemoteMapper: CounterRemoteMapper
) : CreateCounterRemoteDataSource {

    override fun createCounter(title: String): Flow<CreateCounterUiState> = flow {
        emit(CreateCounterUiState.Creating)

        val response = countersApi.createCounter(CreateCounterRequestModel(title))

        if (response.isSuccessful) {
            response.body()?.let {
                emit(CreateCounterUiState.Success(counterRemoteMapper.fromRemote(it)))
            }
        } else {
            emit(CreateCounterUiState.Error("${response.code()}, ${response.message()}"))
        }
    }.catch {
        emit(CreateCounterUiState.Error(it.message.toString()))
    }
}