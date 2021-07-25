package com.cornershop.counterstest.presentation.widgets.exampleSection

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.getStringOrThrow
import androidx.core.content.res.getTextArrayOrThrow
import androidx.core.content.withStyledAttributes
import com.cornershop.counterstest.R
import com.cornershop.counterstest.databinding.ExampleSectionLayoutBinding

class ExampleSection(context: Context, attributeSet: AttributeSet) : ConstraintLayout(context, attributeSet), ExampleAdapter.ExampleAdapterListener {

    private val binding: ExampleSectionLayoutBinding = ExampleSectionLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    private lateinit var title: String
    private lateinit var examplesList: Array<CharSequence>
    private var listener: ExampleSectionListener? = null

    init {
        context.withStyledAttributes(attributeSet, R.styleable.ExampleSection) {
            title = getStringOrThrow(R.styleable.ExampleSection_sectionTitle)
            examplesList = getTextArrayOrThrow(R.styleable.ExampleSection_android_entries)
        }

        binding.textViewTitle.text = title
        binding.rvExamples.adapter = ExampleAdapter(context, examplesList, this)
    }

    override fun onExampleClicked(example: String) {
        listener?.onExampleClicked(example)
    }

    fun setListener(listener: ExampleSectionListener) {
        this.listener = listener
    }

    interface ExampleSectionListener {
        fun onExampleClicked(example: String)
    }
}