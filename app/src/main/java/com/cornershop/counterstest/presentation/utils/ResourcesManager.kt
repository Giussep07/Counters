package com.cornershop.counterstest.presentation.utils

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

class ResourcesManager @Inject constructor(private val context: Context) {

    fun getString(@StringRes resId: Int): String {
        return context.getString(resId)
    }

    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {
        return context.getString(resId, *formatArgs)
    }
}