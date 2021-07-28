package com.cornershop.counterstest.presentation.home.adapter

import androidx.recyclerview.selection.ItemKeyProvider
import com.cornershop.counterstest.presentation.model.CounterItem

class CounterItemKeyProvider(private val adapter: CounterAdapter) :
    ItemKeyProvider<CounterItem.CounterUiModel>(SCOPE_CACHED) {

    override fun getKey(position: Int): CounterItem.CounterUiModel? = when (val item = adapter.currentList[position]) {
        is CounterItem.CounterUiModel -> item
        else -> null
    }

    override fun getPosition(key: CounterItem.CounterUiModel): Int =
        adapter.currentList.indexOfFirst { it is CounterItem.CounterUiModel && it.id == key.id }
}