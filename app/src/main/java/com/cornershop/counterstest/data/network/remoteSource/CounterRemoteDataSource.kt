package com.cornershop.counterstest.data.network.remoteSource

import com.cornershop.counterstest.domain.model.Counter

interface CounterRemoteDataSource {
    suspend fun getCounters(): List<Counter>
    suspend fun decreaseCounter(id: String): List<Counter>
    suspend fun increaseCounter(id: String): List<Counter>
    suspend fun deleteCounters(ids: List<String>): List<Counter>
    suspend fun createCounter(title: String): List<Counter>
}
