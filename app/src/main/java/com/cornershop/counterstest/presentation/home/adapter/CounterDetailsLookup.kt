package com.cornershop.counterstest.presentation.home.adapter

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.cornershop.counterstest.presentation.model.CounterItem

class CounterDetailsLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<CounterItem.CounterUiModel>() {

    override fun getItemDetails(event: MotionEvent): ItemDetails<CounterItem.CounterUiModel>? {
        val view = recyclerView.findChildViewUnder(event.x, event.y)
        if (view != null) {
            return when (val holder = recyclerView.getChildViewHolder(view)) {
                is CounterAdapter.CounterVH -> holder.getItemDetails()
                else -> null
            }
        }
        return null
    }
}