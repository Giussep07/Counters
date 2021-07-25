package com.cornershop.counterstest.presentation.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.counter_toolbar.view.*

class CounterToolbar(context: Context, attributeSet: AttributeSet) : ConstraintLayout(context, attributeSet) {

    fun setCloseClickListener(action: (View) -> Unit) {
        btnClose.setOnClickListener(action)
    }

    fun setBackClickListener(action: (View) -> Unit) {
        btnBack.setOnClickListener(action)
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

    fun hideSaveButton() {
        btnSave.isGone = true
    }

    fun showBackButton() {
        btnClose.isGone = true
        btnBack.isVisible = true
    }

    fun setToolbarTitle(title: String) {
        textViewToolbarTitle.text = title
    }

}