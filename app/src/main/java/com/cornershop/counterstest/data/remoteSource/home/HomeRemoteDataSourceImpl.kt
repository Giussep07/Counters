package com.cornershop.counterstest.data.remoteSource.home

import com.cornershop.counterstest.data.apiService.CountersApi
import com.cornershop.counterstest.data.mapper.CounterRemoteMapper
import com.cornershop.counterstest.data.model.request.CounterDecreaseRequestModel
import com.cornershop.counterstest.data.model.request.CounterIncreaseRequestModel
import com.cornershop.counterstest.data.model.request.DeleteCounterRequestModel
import com.cornershop.counterstest.data.model.response.CounterResponseModel
import com.cornershop.counterstest.presentation.state.home.HomeDecreaseCounterUiState
import com.cornershop.counterstest.presentation.state.home.HomeDeleteCounterUiState
import com.cornershop.counterstest.presentation.state.home.HomeIncreaseCounterUiState
import com.cornershop.counterstest.presentation.state.home.HomeUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeRemoteDataSourceImpl @Inject constructor(private val countersApi: CountersApi,
    private val counterRemoteMapper: CounterRemoteMapper
) : HomeRemoteDataSource {

    override fun getCounters(): Flow<HomeUiState> = flow {
        emit(HomeUiState.Loading)

        kotlinx.coroutines.delay(TimeUnit.SECONDS.toMillis(2))

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

    override fun deleteCounters(ids: List<String>): Flow<HomeDeleteCounterUiState> = flow {
        var response: Response<List<CounterResponseModel>>? = null

        emit(HomeDeleteCounterUiState.Deleting)

        val i: Iterator<String> = ids.iterator()
        while (i.hasNext()) {
            response = countersApi.deleteCounter(DeleteCounterRequestModel(i.next()))

            if (!response.isSuccessful) {
                emit(HomeDeleteCounterUiState.Error("${response.code()}, ${response.message()}"))
                break
            }
        }

        response?.body()?.let {
            emit(HomeDeleteCounterUiState.Success(counterRemoteMapper.fromRemote(it)))
        }
    }.catch {
        emit(HomeDeleteCounterUiState.Error(it.message.toString()))
    }
}
