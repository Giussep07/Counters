package com.cornershop.counterstest.domain.repository

import com.cornershop.counterstest.domain.model.Counter

interface CounterRepository {
    suspend fun getCounters(): List<Counter>
    suspend fun decreaseCounter(id: String): List<Counter>
    suspend fun increaseCounter(id: String): List<Counter>
    suspend fun deleteCounters(ids: List<String>): List<Counter>
    suspend fun createCounter(title: String): List<Counter>
    suspend fun searchCounter(query: String): List<Counter>
}
