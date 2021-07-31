package com.cornershop.counterstest.data.repository

import com.cornershop.counterstest.data.database.localSource.CounterLocalDataSource
import com.cornershop.counterstest.data.database.mapper.CounterLocalMapper
import com.cornershop.counterstest.data.network.remoteSource.CounterRemoteDataSource
import com.cornershop.counterstest.domain.model.Counter
import com.cornershop.counterstest.domain.repository.CounterRepository
import javax.inject.Inject

class CounterRepositoryImpl @Inject constructor(private val counterRemoteDataSource: CounterRemoteDataSource,
    private val counterLocalDataSource: CounterLocalDataSource,
    private val counterLocalMapper: CounterLocalMapper): CounterRepository {

    override suspend fun getCounters(): List<Counter> {
        val counters = counterRemoteDataSource.getCounters()
        insertCountersLocally(counters)
        return counters
    }

    override suspend fun decreaseCounter(id: String): List<Counter> {
        val counters = counterRemoteDataSource.decreaseCounter(id)
        insertCountersLocally(counters)
        return counters
    }

    override suspend fun increaseCounter(id: String): List<Counter> {
        val counters = counterRemoteDataSource.increaseCounter(id)
        insertCountersLocally(counters)
        return counters
    }

    override suspend fun deleteCounters(ids: List<String>): List<Counter> {
        val counters = counterRemoteDataSource.deleteCounters(ids)
        counters.map { counterLocalMapper.fromDomain(it) }
        counterLocalDataSource.deleteCounters(ids)
        return counters
    }

    override suspend fun createCounter(title: String): List<Counter> {
        val counters = counterRemoteDataSource.createCounter(title)
        insertCountersLocally(counters)
        return counters
    }

    private suspend fun insertCountersLocally(counters: List<Counter>) {
        counters.map { counterLocalMapper.fromDomain(it) }
            .also { counterLocalDataSource.insertCounters(it) }
    }
}
