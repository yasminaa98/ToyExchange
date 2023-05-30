package com.example.toyexchange.theme.ui.fragments.AuthenticationFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.toyexchange.R
import com.example.toyexchange.databinding.SignUpFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment:Fragment(R.layout.sign_up_fragment) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = SignUpFragmentBinding.inflate(inflater, container, false)
        val emailPattern = "[a-zA-Z0-9._-]*@[a-z]+\\.+[a-z]+"
        binding.nextButton.setOnClickListener {
            val firstname = binding.firstname.text.toString()
            val lastname = binding.lastname.text.toString()
            val email = binding.email.text.toString()
            val username = binding.username.text.toString()
            val address = binding.address.text.toString()
            val avg_response = binding.avgResponse.text.toString()
            val phone = binding.phone.text

            var hasError = false

            if (firstname.isEmpty()) {
                binding.firstname.error = "First name field is required"
                hasError = true
            }
            if (lastname.isEmpty()) {
                binding.lastname.error = "Last name field is required"
                hasError = true
            }
            if (email.isEmpty() || !email.matches(emailPattern.toRegex())) {
                binding.email.error = "Invalid email address"
                hasError = true
            }
            if (username.isEmpty()) {
                binding.username.error = "Username field is required"
                hasError = true
            }
            if (address.isEmpty()) {
                binding.address.error = "Address field is required"
                hasError = true
            }
            if (avg_response.isEmpty()) {
                binding.avgResponse.error = "Average response field is required"
                hasError = true
            }
            if (phone.isEmpty()) {
                binding.phone.error = "Phone field is required"
                hasError = true
            }

            if (!hasError) {
                val bundle = bundleOf(
                    "firstname" to firstname,
                    "lastname" to lastname, "email" to email,
                    "username" to username, "address" to address,
                    "avg_response" to avg_response, "phone" to phone.toString()
                )
                findNavController().navigate(
                    R.id.action_signUpFragment_to_addPasswordFragment,
                    bundle
                )
            }
        }

        return binding.root

    }
}