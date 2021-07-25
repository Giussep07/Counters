package com.cornershop.counterstest.presentation.examples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.cornershop.counterstest.R
import com.cornershop.counterstest.databinding.FragmentExamplesBinding
import com.cornershop.counterstest.di.viewModel.ViewModelFactory
import com.cornershop.counterstest.presentation.base.BaseBindingFragment
import com.cornershop.counterstest.presentation.state.createCounter.CreateCounterUiState
import com.cornershop.counterstest.presentation.widgets.exampleSection.ExampleSection
import javax.inject.Inject

class ExamplesFragment : BaseBindingFragment<FragmentExamplesBinding>(), ExampleSection.ExampleSectionListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: ExamplesViewModel by viewModels { viewModelFactory }

    override fun bindView(inflater: LayoutInflater, container: ViewGroup?): FragmentExamplesBinding =
            FragmentExamplesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()

        binding.exampleSectionDrinks.setListener(this)
        binding.exampleSectionFood.setListener(this)
        binding.exampleSectionMisc.setListener(this)

        viewModel.createCounterUiState.observe(viewLifecycleOwner) {
            when (it) {
                is CreateCounterUiState.Creating -> showLoading()
                is CreateCounterUiState.Success -> {
                    findNavController().navigate(ExamplesFragmentDirections.actionExamplesFragmentToHomeFragment())
                }
                is CreateCounterUiState.Error -> {
                    // TODO: handle error
                }
            }
        }
    }

    override fun onExampleClicked(example: String) {
        viewModel.createCounter(example)
    }

    private fun setupToolbar() {
        binding.toolbar.root.apply {
            setToolbarTitle(getString(R.string.examples))
            showBackButton()
            setBackClickListener { findNavController().popBackStack() }
            hideSaveButton()
        }
    }

    private fun showLoading() {
        binding.textViewDescription.isGone = true
        binding.exampleSectionDrinks.isGone = true
        binding.exampleSectionFood.isGone = true
        binding.exampleSectionMisc.isGone = true
        binding.progressLoading.isVisible = true
    }

    private fun hideLoading() {
        binding.textViewDescription.isVisible = true
        binding.exampleSectionDrinks.isVisible = true
        binding.exampleSectionFood.isVisible = true
        binding.exampleSectionMisc.isVisible = true
        binding.progressLoading.isGone = true
    }

}