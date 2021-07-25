package com.cornershop.counterstest.presentation.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.create_counter_toolbar.view.*

class CreateCounterToolbar(context: Context, attributeSet: AttributeSet) : ConstraintLayout(context, attributeSet) {

    fun setBackClickListener(action: (View) -> Unit) {
        btnClose.setOnClickListener(action)
    }

    fun setSaveClickListener(action: (View) -> Unit) {
        btnSave.setOnClickListener(action)
    }

    fun isSaveEnabled(enabled: Boolean) {
        btnSave.isEnabled = enabled
    }

    fun showLoading() {
        btnSave.isInvisible = true
        progressLoading.isVisible = true
    }

    fun hideLoading() {
        btnSave.isVisible = true
        progressLoading.isGone = true
    }

}