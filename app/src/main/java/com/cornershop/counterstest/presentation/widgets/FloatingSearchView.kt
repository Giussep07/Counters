package com.cornershop.counterstest.presentation.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.cornershop.counterstest.databinding.FloatingSearchViewBinding

class FloatingSearchView(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet) {

    private val binding = FloatingSearchViewBinding.inflate(LayoutInflater.from(context), this, true)

    private var listener: FloatingSearchViewListener? = null

    private var inputManager: InputMethodManager? = null

    fun setListener(listener: FloatingSearchViewListener) {
        this.listener = listener
    }

    init {
        inputManager = ContextCompat.getSystemService(context, InputMethodManager::class.java)

        binding.etSearch.doOnTextChanged { text, _, _, _ -> listener?.onQuery(text.toString()) }

        binding.etSearch.setOnFocusChangeListener { _, isFocused ->
            binding.imageViewClose.isVisible = isFocused
        }

        binding.imageViewClose.setOnClickListener {
            binding.etSearch.apply {
                clearFocus()
                setText("")
                inputManager?.hideSoftInputFromWindow(this@FloatingSearchView.windowToken, 0)
            }
        }
    }

    interface FloatingSearchViewListener {
        fun onQuery(query: String)
    }

}