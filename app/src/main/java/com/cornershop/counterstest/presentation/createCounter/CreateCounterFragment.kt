package com.cornershop.counterstest.presentation.createCounter

import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.cornershop.counterstest.R
import com.cornershop.counterstest.databinding.FragmentCreateCounterBinding
import com.cornershop.counterstest.di.viewModel.ViewModelFactory
import com.cornershop.counterstest.presentation.base.BaseBindingFragment
import com.cornershop.counterstest.presentation.state.createCounter.CreateCounterUiState
import javax.inject.Inject

class CreateCounterFragment : BaseBindingFragment<FragmentCreateCounterBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: CreateCounterViewModel by viewModels { viewModelFactory }

    override fun bindView(
            inflater: LayoutInflater,
            container: ViewGroup?
    ): FragmentCreateCounterBinding =
            FragmentCreateCounterBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setDisclaimerText()

        binding.tieCounterName.doOnTextChanged { text, _, _, _ ->
            binding.toolbar.root.isSaveEnabled(!text.isNullOrEmpty())
        }

        viewModel.createCounterUiState.observe(viewLifecycleOwner) {
            when (it) {
                is CreateCounterUiState.Creating -> {
                    binding.toolbar.root.showLoading()
                }
                is CreateCounterUiState.Success -> {
                    findNavController().popBackStack()
                }
                is CreateCounterUiState.Error -> {
                    Toast.makeText(requireContext(), "Error: ${it.error}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupToolbar() {
        binding.toolbar.root.apply {
            setToolbarTitle(getString(R.string.create_counter))
            setCloseClickListener { findNavController().popBackStack() }
            setSaveClickListener {
                viewModel.createCounter(binding.tieCounterName.text.toString())
            }
        }
    }

    private fun setDisclaimerText() {
        val disclaimer = getString(R.string.create_counter_disclaimer)
        val exampleLink = getString(R.string.create_counter_disclaimer_examples)
        binding.textViewDisclaimer.movementMethod = LinkMovementMethod()
        binding.textViewDisclaimer.text = SpannableString(disclaimer).apply {
            val startFrom = disclaimer.indexOf(exampleLink)
            val endTo = startFrom + exampleLink.length
            setSpan(UnderlineSpan(), startFrom, endTo, 0)
            setSpan(
                    object : ClickableSpan() {
                        override fun onClick(p0: View) {
                            this@CreateCounterFragment.findNavController()
                                    .navigate(CreateCounterFragmentDirections.actionCreateCounterFragmentToExamplesFragment())
                        }
                    },
                    startFrom, endTo, 0
            )
        }
    }
}