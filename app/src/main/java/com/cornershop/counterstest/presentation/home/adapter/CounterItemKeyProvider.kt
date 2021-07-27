package com.cornershop.counterstest.presentation.home.adapter

import androidx.recyclerview.selection.ItemKeyProvider
import com.cornershop.counterstest.presentation.model.CounterItem

class CounterItemKeyProvider(private val adapter: CounterAdapter) :
    ItemKeyProvider<String>(SCOPE_CACHED) {

    override fun getKey(position: Int): String? = when (val item = adapter.currentList[position]) {
        is CounterItem.CounterUiModel -> item.id
        else -> null
    }

    override fun getPosition(key: String): Int =
        adapter.currentList.indexOfFirst { it is CounterItem.CounterUiModel && it.id == key }
}