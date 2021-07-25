package com.cornershop.counterstest.presentation.model

import androidx.recyclerview.widget.DiffUtil

sealed class CounterItem {

    data class CounterHeaderUiModel(val items: Int, val times: Int) : CounterItem()

    data class CounterUiModel(val id: String, val title: String, val count: Int) : CounterItem()

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<CounterItem>() {
            override fun areItemsTheSame(oldItem: CounterItem, newItem: CounterItem): Boolean {
                return when {
                    newItem is CounterHeaderUiModel && oldItem is CounterHeaderUiModel -> true
                    newItem is CounterUiModel && oldItem is CounterUiModel -> true
                    else -> false
                }
            }

            override fun areContentsTheSame(oldItem: CounterItem, newItem: CounterItem): Boolean {
                return when {
                    newItem is CounterHeaderUiModel && oldItem is CounterHeaderUiModel -> newItem == oldItem
                    newItem is CounterUiModel && oldItem is CounterUiModel -> newItem == oldItem
                    else -> false
                }
            }
        }
    }
}
