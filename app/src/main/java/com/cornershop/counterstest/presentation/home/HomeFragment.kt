package com.cornershop.counterstest.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.cornershop.counterstest.R
import com.cornershop.counterstest.databinding.FragmentHomeBinding
import com.cornershop.counterstest.di.viewModel.ViewModelFactory
import com.cornershop.counterstest.presentation.base.BaseBindingFragment
import com.cornershop.counterstest.presentation.home.adapter.CounterAdapter
import com.cornershop.counterstest.presentation.mapper.CounterPresentationMapper
import com.cornershop.counterstest.presentation.model.CounterItem
import com.cornershop.counterstest.presentation.state.home.HomeDecreaseCounterUiState
import com.cornershop.counterstest.presentation.state.home.HomeIncreaseCounterUiState
import com.cornershop.counterstest.presentation.state.home.HomeUiState
import javax.inject.Inject

class HomeFragment : BaseBindingFragment<FragmentHomeBinding>(),
    CounterAdapter.CounterAdapterListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject lateinit var counterPresentationMapper: CounterPresentationMapper

    private val viewModel: HomeViewModel by viewModels { viewModelFactory }
    private lateinit var adapter: CounterAdapter

    override fun bindView(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding =
        FragmentHomeBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupRefreshLayout()

        binding.btnRetry.setOnClickListener {
            viewModel.getCounters()
        }

        binding.btnAddCounter.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCreateCounterFragment())
        }

        viewModel.getCounters()

        viewModel.homeUiState.observe(viewLifecycleOwner) {
            when (it) {
                is HomeUiState.Loading -> {
                    binding.progressLoading.isVisible = true
                    binding.groupNoContent.isGone = true
                    binding.rvCounters.isGone = true
                    binding.groupError.isGone = true
                }
                is HomeUiState.Success -> {
                    binding.srlCounters.isRefreshing = false
                    if (it.counters.isEmpty()) {
                        binding.progressLoading.isGone = true
                        binding.groupNoContent.isVisible = true
                    } else {
                        binding.progressLoading.isGone = true
                        binding.rvCounters.isVisible = true
                        adapter.submitList(counterPresentationMapper.toUiModel(it.counters))
                    }
                }
                is HomeUiState.Error -> {
                    binding.srlCounters.isEnabled = false
                    binding.progressLoading.isGone = true
                    binding.groupError.isVisible = true
                    println("Counter Error: ${it.errorMessage}")
                }
            }
        }

        viewModel.homeDecreaseUiState.observe(viewLifecycleOwner) {
            when (it) {
                is HomeDecreaseCounterUiState.Loading -> {
                    binding.progressLoading.isVisible = true
                    binding.groupError.isGone = true
                }
                is HomeDecreaseCounterUiState.Success -> {
                    if (it.counters.isEmpty()) {
                        binding.progressLoading.isGone = true
                        binding.groupNoContent.isVisible = true
                    } else {
                        binding.progressLoading.isGone = true
                        adapter.submitList(counterPresentationMapper.toUiModel(it.counters))
                    }
                }
                is HomeDecreaseCounterUiState.Error -> {
                    binding.progressLoading.isGone = true
                    binding.groupError.isVisible = true
                    println("Counter Error: ${it.errorMessage}")
                }
            }
        }

        viewModel.homeIncreaseUiState.observe(viewLifecycleOwner) {
            when (it) {
                is HomeIncreaseCounterUiState.Loading -> {
                    binding.progressLoading.isVisible = true
                    binding.groupError.isGone = true
                }
                is HomeIncreaseCounterUiState.Success -> {
                    if (it.counters.isEmpty()) {
                        binding.progressLoading.isGone = true
                        binding.groupNoContent.isVisible = true
                    } else {
                        binding.progressLoading.isGone = true
                        adapter.submitList(counterPresentationMapper.toUiModel(it.counters))
                    }
                }
                is HomeIncreaseCounterUiState.Error -> {
                    binding.progressLoading.isGone = true
                    binding.groupError.isVisible = true
                    println("Counter Error: ${it.errorMessage}")
                }
            }
        }
    }

    override fun onDecreaseClicked(counterUiModel: CounterItem.CounterUiModel) {
        viewModel.decreaseCounter(counterUiModel)
    }

    override fun onIncreaseClicked(counterUiModel: CounterItem.CounterUiModel) {
        viewModel.increaseCounter(counterUiModel)
    }

    private fun setupRecyclerView() {
        binding.rvCounters.adapter = CounterAdapter(requireContext(), this).also {
            adapter = it
        }
    }

    private fun setupRefreshLayout() {
        binding.srlCounters.setColorSchemeResources(R.color.orange)

        binding.srlCounters.setOnRefreshListener {
            viewModel.getCounters()
        }
    }
}
