package com.cornershop.counterstest.data.network.mapper

import com.cornershop.counterstest.data.network.model.response.CounterResponseModel
import com.cornershop.counterstest.domain.model.Counter

class CounterRemoteMapper {

    fun fromRemote(counters: List<CounterResponseModel>): List<Counter> {
        return counters.map { Counter(it.id, it.title, it.count) }
    }
}