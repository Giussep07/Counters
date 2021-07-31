package com.cornershop.counterstest.data.database.mapper

import com.cornershop.counterstest.data.database.model.CounterEntity
import com.cornershop.counterstest.domain.model.Counter

class CounterLocalMapper {
    fun fromDomain(counter: Counter): CounterEntity {
        return CounterEntity(counter.id, counter.title, counter.count)
    }
}