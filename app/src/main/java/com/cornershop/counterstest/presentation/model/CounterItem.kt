package com.cornershop.counterstest.presentation.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.android.parcel.Parcelize

sealed class CounterItem {

    data class CounterHeaderUiModel(val items: Int, val times: Int) : CounterItem()

    @Parcelize
    data class CounterUiModel(val id: String, val title: String, val count: Int) : CounterItem(), Parcelable

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
