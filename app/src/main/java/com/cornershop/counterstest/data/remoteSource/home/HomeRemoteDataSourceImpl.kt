package com.cornershop.counterstest.data.remoteSource.home

import com.cornershop.counterstest.data.apiService.CountersApi
import com.cornershop.counterstest.data.mapper.CounterRemoteMapper
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

        kotlinx.coroutines.delay(TimeUnit.SECONDS.toMillis(5))

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
}