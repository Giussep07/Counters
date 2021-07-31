package com.cornershop.counterstest.data.database.localSource

import com.cornershop.counterstest.data.database.model.CounterEntity

interface CounterLocalDataSource {
    suspend fun insertCounters(counters: List<CounterEntity>)
    suspend fun deleteCounters(countersId: List<String>)
}