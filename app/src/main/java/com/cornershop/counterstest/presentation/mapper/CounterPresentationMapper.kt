package com.cornershop.counterstest.presentation.mapper

import com.cornershop.counterstest.domain.model.Counter
import com.cornershop.counterstest.presentation.model.CounterItem

class CounterPresentationMapper {
    fun toUiModel(counters: List<Counter>): List<CounterItem> {
        val countersMutable = mutableListOf<CounterItem>()

        val items = counters.size
        val times = counters.sumBy { it.count }

        val header = CounterItem.CounterHeaderUiModel(items, times)
        countersMutable.add(header)
        countersMutable.addAll(counters.map {
            CounterItem.CounterUiModel(
                it.id,
                it.title,
                it.count
            )
        })

        return countersMutable
    }
}
