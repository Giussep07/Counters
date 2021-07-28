package com.cornershop.counterstest.presentation.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CounterToShare(val id: String, val description: String): Parcelable {
    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<CounterToShare>() {
            override fun areItemsTheSame(oldItem: CounterToShare, newItem: CounterToShare): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CounterToShare, newItem: CounterToShare): Boolean {
                return oldItem == newItem
            }
        }
    }
}
