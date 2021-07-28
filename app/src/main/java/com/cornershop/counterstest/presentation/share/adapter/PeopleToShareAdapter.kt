package com.cornershop.counterstest.presentation.share.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cornershop.counterstest.databinding.ItemCounterToShareBinding
import com.cornershop.counterstest.databinding.ItemSharePeopleBinding
import com.cornershop.counterstest.presentation.model.CounterToShare
import com.cornershop.counterstest.presentation.model.PeopleToShare

class PeopleToShareAdapter(private val context: Context, private val listener: PeopleToShareListener):
    ListAdapter<PeopleToShare, PeopleToShareAdapter.PeopleToShareVH>(PeopleToShare.DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleToShareVH {
        val inflater = LayoutInflater.from(context)
        return PeopleToShareVH(ItemSharePeopleBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: PeopleToShareVH, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PeopleToShareVH(private val binding: ItemSharePeopleBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PeopleToShare) {
            binding.imageViewPicture.setImageResource(item.imageResId)
            binding.textViewName.text = item.name

            binding.root.setOnClickListener { listener.onPeopleClicked(item) }
        }
    }

    interface PeopleToShareListener {
        fun onPeopleClicked(item: PeopleToShare)
    }
}