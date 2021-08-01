package com.cornershop.counterstest.data.database.localSource

import com.cornershop.counterstest.data.database.CounterDao
import com.cornershop.counterstest.data.database.model.CounterEntity
import javax.inject.Inject

class CounterLocalDataSourceImpl @Inject constructor(private val countersDao: CounterDao): CounterLocalDataSource {

    override suspend fun insertCounters(counters: List<CounterEntity>) {
        countersDao.insertCounters(counters)
    }

    override suspend fun deleteCounters(countersId: List<String>) {
        countersDao.deleteCounters(countersId)
    }

    override fun searchCounter(query: String): List<CounterEntity> {
        return countersDao.searchCounter(query)
    }
}