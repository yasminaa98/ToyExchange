package com.example.toyexchange.theme.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.toyexchange.Presentation.ToysViewModel.ForgotPasswordViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.ForgotPasswordFragmentBinding
import com.example.toyexchange.databinding.ResetPasswordFragmentBinding
import com.example.toyexchange.databinding.SignInFragmentBinding
import com.example.toyexchange.theme.ui.fragments.AuthenticationFragment.SignInFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPasswordFragment:Fragment(R.layout.reset_password_fragment) {
    private lateinit var forgotPasswordViewModel: ForgotPasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = ResetPasswordFragmentBinding.inflate(inflater, container, false)
        forgotPasswordViewModel = ViewModelProvider(this).get(ForgotPasswordViewModel::class.java)
        val sharedPreferences =
            requireActivity().getSharedPreferences("tokenMessage", Context.MODE_PRIVATE)
        val tokenMsg =sharedPreferences.getString("tokenMessage",null)
        Log.i("tokenMessage", tokenMsg.toString())

        binding.save.setOnClickListener {
            val newPassword = binding.newPassword.text.toString()
            Log.i("newPassword", newPassword)
            Toast.makeText(
                requireContext(),
                "Password changes with success ",
                Toast.LENGTH_LONG
            ).show()
            //findNavController().navigate(R.id.action_resetPasswordFragment_to_signInFragment)
        }

        return binding.root
    }
}