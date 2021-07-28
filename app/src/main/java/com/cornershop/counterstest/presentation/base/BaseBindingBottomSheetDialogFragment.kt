package com.cornershop.counterstest.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.support.DaggerFragment

abstract class BaseBindingBottomSheetDialogFragment<B : ViewBinding> : BottomSheetDialogFragment() {

    private var _binding: B? = null
    protected val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = bindView(inflater, container)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    abstract fun bindView(inflater: LayoutInflater, container: ViewGroup?): B
}