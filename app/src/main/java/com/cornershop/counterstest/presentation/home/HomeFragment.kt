package com.cornershop.counterstest.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.cornershop.counterstest.databinding.FragmentHomeBinding
import com.cornershop.counterstest.di.viewModel.ViewModelFactory
import com.cornershop.counterstest.presentation.base.BindingFragment
import com.cornershop.counterstest.presentation.state.HomeUiState
import javax.inject.Inject

class HomeFragment : BindingFragment<FragmentHomeBinding>() {

    @Inject lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: HomeViewModel by viewModels { viewModelFactory }

    override fun bindView(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding =
        FragmentHomeBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCounters()

        viewModel.homeUiState.observe(viewLifecycleOwner) {
            when (it) {
                is HomeUiState.Loading -> {
                    binding.progressLoading.isVisible = true
                    binding.textViewError.isGone = true
                }
                is HomeUiState.Success -> {
                    it.counters.forEach { counter ->
                        println("Counter: ${counter.id}, ${counter.title}, ${counter.count}")
                    }
                }
                is HomeUiState.Error -> {
                    binding.progressLoading.isGone = true
                    binding.textViewError.isVisible = true
                    println("Counter Error: ${it.errorMessage}")
                }
            }
        }
    }
}