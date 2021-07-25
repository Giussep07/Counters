package com.cornershop.counterstest.presentation.widgets.exampleSection

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cornershop.counterstest.databinding.ItemExampleBinding

class ExampleAdapter(private val context: Context,
        private val examples: Array<CharSequence>,
        private val listener: ExampleAdapterListener) : RecyclerView.Adapter<ExampleAdapter.ExampleVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleVH {
        val binding = ItemExampleBinding.inflate(LayoutInflater.from(context), parent, false)
        return ExampleVH(binding, listener)
    }

    override fun onBindViewHolder(holder: ExampleVH, position: Int) {
        holder.bind(examples[position])
    }

    override fun getItemCount(): Int = examples.size

    inner class ExampleVH(private val binding: ItemExampleBinding, private val listener: ExampleAdapterListener) : RecyclerView.ViewHolder(binding.root) {

        fun bind(charSequence: CharSequence) {
            binding.chipExample.text = charSequence.toString()
            binding.chipExample.setOnClickListener { listener.onExampleClicked(charSequence.toString()) }
        }
    }

    interface ExampleAdapterListener {
        fun onExampleClicked(example: String)
    }
}