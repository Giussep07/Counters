package com.cornershop.counterstest.presentation.mapper

import android.content.Context
import com.cornershop.counterstest.R
import com.cornershop.counterstest.domain.model.Counter
import com.cornershop.counterstest.presentation.model.CounterItem
import com.cornershop.counterstest.presentation.model.CounterToShare

class CounterPresentationMapper(private val context: Context) {

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

    fun fromUIModelToCounterToShare(counters: List<CounterItem.CounterUiModel>) : List<CounterToShare> {
        return counters.map { CounterToShare(it.id, context.getString(R.string.n_per_counter_name, it.count, it.title)) }
    }
}
