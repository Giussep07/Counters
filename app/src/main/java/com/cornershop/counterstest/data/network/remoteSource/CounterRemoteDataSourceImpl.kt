package com.cornershop.counterstest.data.network.remoteSource

import com.cornershop.counterstest.data.network.apiService.CountersApi
import com.cornershop.counterstest.data.network.mapper.CounterRemoteMapper
import com.cornershop.counterstest.data.network.model.request.CounterDecreaseRequestModel
import com.cornershop.counterstest.data.network.model.request.CounterIncreaseRequestModel
import com.cornershop.counterstest.data.network.model.request.CreateCounterRequestModel
import com.cornershop.counterstest.data.network.model.request.DeleteCounterRequestModel
import com.cornershop.counterstest.data.network.model.response.CounterResponseModel
import com.cornershop.counterstest.domain.model.Counter
import retrofit2.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CounterRemoteDataSourceImpl @Inject constructor(private val countersApi: CountersApi,
    private val counterRemoteMapper: CounterRemoteMapper
) : CounterRemoteDataSource {

    override suspend fun getCounters(): List<Counter> {
        kotlinx.coroutines.delay(TimeUnit.SECONDS.toMillis(2))
        return counterRemoteMapper.fromRemote(countersApi.getCounters())
    }

    override suspend fun decreaseCounter(id: String): List<Counter> {
        return counterRemoteMapper.fromRemote(countersApi.decreaseCounter(
            CounterDecreaseRequestModel(id)
        ))
    }

    override suspend fun increaseCounter(id: String): List<Counter> {
        return counterRemoteMapper.fromRemote(countersApi.increaseCounter(
            CounterIncreaseRequestModel(id)
        ))
    }

    override suspend fun deleteCounters(ids: List<String>): List<Counter> {
        var counters: List<CounterResponseModel> = listOf()
        ids.forEach { counters = countersApi.deleteCounter(DeleteCounterRequestModel(it)) }
        return counterRemoteMapper.fromRemote(counters)
    }

    override suspend fun createCounter(title: String): List<Counter> {
        return counterRemoteMapper.fromRemote(countersApi.createCounter(CreateCounterRequestModel(title)))
    }
}
