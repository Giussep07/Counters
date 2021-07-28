package com.cornershop.counterstest.presentation.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cornershop.counterstest.R
import com.cornershop.counterstest.databinding.ItemCounterBinding
import com.cornershop.counterstest.databinding.ItemCounterHeaderBinding
import com.cornershop.counterstest.presentation.model.CounterItem

class CounterAdapter(private val context: Context, private val listener: CounterAdapterListener) :
    ListAdapter<CounterItem, RecyclerView.ViewHolder>(CounterItem.DiffCallback) {

    lateinit var selectionTracker: SelectionTracker<CounterItem.CounterUiModel>

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CounterItem.CounterHeaderUiModel -> COUNTER_HEADER_TYPE
            is CounterItem.CounterUiModel -> COUNTER_TYPE
            else -> throw IllegalStateException("Unknown view type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        return when (viewType) {
            COUNTER_HEADER_TYPE -> {
                val binding = ItemCounterHeaderBinding.inflate(inflater, parent, false)
                CounterHeaderVH(binding)
            }
            COUNTER_TYPE -> {
                val binding = ItemCounterBinding.inflate(inflater, parent, false)
                CounterVH(binding, listener)
            }
            else -> throw IllegalStateException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is CounterItem.CounterHeaderUiModel -> (holder as CounterHeaderVH).bind(item)
            is CounterItem.CounterUiModel -> (holder as CounterVH).bind(item, selectionTracker.isSelected(item))
        }
    }

    inner class CounterHeaderVH(private val binding: ItemCounterHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(counterHeaderUiModel: CounterItem.CounterHeaderUiModel) {
            binding.textViewItems.text = context.getString(R.string.n_items, counterHeaderUiModel.items)
            binding.textViewTimes.text = context.getString(R.string.n_times, counterHeaderUiModel.times)
        }
    }

    inner class CounterVH(private val binding: ItemCounterBinding, private val listener: CounterAdapterListener) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(counterUiModel: CounterItem.CounterUiModel, isActivated: Boolean) {
            binding.textViewCounterName.text = counterUiModel.title
            binding.textViewCounterCount.text = counterUiModel.count.toString()

            binding.imageViewDecrease.isEnabled = counterUiModel.count != 0
            binding.textViewCounterCount.isEnabled = counterUiModel.count != 0

            binding.imageViewIncrease.setOnClickListener { listener.onIncreaseClicked(counterUiModel) }

            binding.imageViewDecrease.setOnClickListener { listener.onDecreaseClicked(counterUiModel) }

            binding.clItemCounter.isActivated = isActivated
            binding.groupButtons.isVisible = !isActivated
            binding.imageViewCheck.isVisible = isActivated
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<CounterItem.CounterUiModel> =
            object : ItemDetailsLookup.ItemDetails<CounterItem.CounterUiModel>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): CounterItem.CounterUiModel? = when (val item = getItem(adapterPosition)) {
                    is CounterItem.CounterUiModel -> item
                    else -> null
                }
            }
    }

    interface CounterAdapterListener {
        fun onDecreaseClicked(counterUiModel: CounterItem.CounterUiModel)
        fun onIncreaseClicked(counterUiModel: CounterItem.CounterUiModel)
    }

    companion object {
        private const val COUNTER_HEADER_TYPE = 1
        private const val COUNTER_TYPE = 2
    }
}
