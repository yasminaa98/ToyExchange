package com.example.toyexchange.theme.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.toyexchange.R
import com.example.toyexchange.databinding.SignUpFragmentBinding

class SignUpFragment:Fragment(R.layout.sign_up_fragment) {
    private lateinit var binding: SignUpFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = SignUpFragmentBinding.inflate(inflater, container, false)
        binding.signUpButton.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_feedToysFragment)
        }
        return binding.root

    }
}