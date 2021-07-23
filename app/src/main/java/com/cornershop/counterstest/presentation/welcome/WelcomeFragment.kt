package com.cornershop.counterstest.presentation.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.cornershop.counterstest.databinding.FragmentWelcomeBinding
import com.cornershop.counterstest.presentation.base.BindingFragment

class WelcomeFragment : BindingFragment<FragmentWelcomeBinding>() {

    override fun bindView(inflater: LayoutInflater, container: ViewGroup?): FragmentWelcomeBinding =
            FragmentWelcomeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        binding.welcomeContent?.buttonStart?.setOnClickListener {
            Toast.makeText(requireContext(), "Hola Pepe", Toast.LENGTH_SHORT).show()
        }
    }

}
