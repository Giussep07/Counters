package com.cornershop.counterstest.presentation.share.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cornershop.counterstest.databinding.ItemCounterToShareBinding
import com.cornershop.counterstest.presentation.model.CounterToShare

class CounterToShareAdapter(private val context: Context, private val listener: CounterToShareListener):
    ListAdapter<CounterToShare, CounterToShareAdapter.CounterToShareVH>(CounterToShare.DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CounterToShareVH {
        val inflater = LayoutInflater.from(context)
        return CounterToShareVH(ItemCounterToShareBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: CounterToShareVH, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CounterToShareVH(private val binding: ItemCounterToShareBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CounterToShare) {
            binding.textViewCounterDescription.text = item.description

            binding.linearCopy.setOnClickListener { listener.onCopyClicked(item.description) }
        }
    }

    interface CounterToShareListener {
        fun onCopyClicked(description: String)
    }
}