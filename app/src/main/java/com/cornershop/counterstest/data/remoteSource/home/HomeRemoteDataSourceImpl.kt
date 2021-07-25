package com.cornershop.counterstest.data.remoteSource.home

import com.cornershop.counterstest.data.apiService.CountersApi
import com.cornershop.counterstest.data.mapper.CounterRemoteMapper
import com.cornershop.counterstest.data.model.request.CounterDecreaseRequestModel
import com.cornershop.counterstest.data.model.request.CounterIncreaseRequestModel
import com.cornershop.counterstest.presentation.state.HomeDecreaseCounterUiState
import com.cornershop.counterstest.presentation.state.HomeIncreaseCounterUiState
import com.cornershop.counterstest.presentation.state.HomeUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeRemoteDataSourceImpl @Inject constructor(private val countersApi: CountersApi,
    private val counterRemoteMapper: CounterRemoteMapper
) : HomeRemoteDataSource {

    override fun getCounters(): Flow<HomeUiState> = flow {
        emit(HomeUiState.Loading)

        kotlinx.coroutines.delay(TimeUnit.SECONDS.toMillis(1))

        val response = countersApi.getCounters()

        if (response.isSuccessful) {
            response.body()?.let {
                emit(HomeUiState.Success(counterRemoteMapper.fromRemote(it)))
            }
        } else {
            emit(HomeUiState.Error("${response.code()}, ${response.message()}"))
        }
    }.catch {
        emit(HomeUiState.Error(it.message.toString()))
    }

    override fun decreaseCounter(id: String): Flow<HomeDecreaseCounterUiState> = flow {
        emit(HomeDecreaseCounterUiState.Loading)

        val response = countersApi.decreaseCounter(CounterDecreaseRequestModel(id))

        if (response.isSuccessful) {
            response.body()?.let {
                emit(HomeDecreaseCounterUiState.Success(counterRemoteMapper.fromRemote(it)))
            }
        } else {
            emit(HomeDecreaseCounterUiState.Error("${response.code()}, ${response.message()}"))
        }
    }.catch {
        emit(HomeDecreaseCounterUiState.Error(it.message.toString()))
    }

    override fun increaseCounter(id: String): Flow<HomeIncreaseCounterUiState> = flow {
        emit(HomeIncreaseCounterUiState.Loading)

        val response = countersApi.increaseCounter(CounterIncreaseRequestModel(id))

        if (response.isSuccessful) {
            response.body()?.let {
                emit(HomeIncreaseCounterUiState.Success(counterRemoteMapper.fromRemote(it)))
            }
        } else {
            emit(HomeIncreaseCounterUiState.Error("${response.code()}, ${response.message()}"))
        }
    }.catch {
        emit(HomeIncreaseCounterUiState.Error(it.message.toString()))
    }
}
