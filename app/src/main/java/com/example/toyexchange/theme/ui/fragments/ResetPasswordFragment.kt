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
import com.example.toyexchange.theme.ui.MainActivity
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
        (activity as MainActivity).setSlideNavigaton(false)
        (activity as MainActivity).setBottomNavigation(false)
        (activity as MainActivity).setToolbar(false)
        forgotPasswordViewModel = ViewModelProvider(this).get(ForgotPasswordViewModel::class.java)
        val sharedPreferences =
            requireActivity().getSharedPreferences("tokenMessage", Context.MODE_PRIVATE)
        val tokenMsg =sharedPreferences.getString("tokenMessage",null)
        Log.i("tokenMessage", tokenMsg.toString())

        binding.save.setOnClickListener {
            val newPassword = binding.newPassword.text.toString()
            val repeatPassword = binding.repeatNewPassword.text.toString()

            if (newPassword.isEmpty() && repeatPassword.isEmpty()) {
                binding.newPassword.error = "New password is required"
                binding.repeatNewPassword.error = "Repeat password is required"
            } else if (newPassword.isEmpty()) {
                binding.newPassword.error = "New password is required"
            } else if (repeatPassword.isEmpty()) {
                binding.repeatNewPassword.error = "Repeat password is required"
            } else if (newPassword != repeatPassword) {
                Toast.makeText(
                    requireContext(),
                    "Passwords do not match",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Log.i("newPassword", newPassword)
                Toast.makeText(
                    requireContext(),
                    "Password changed successfully",
                    Toast.LENGTH_LONG
                ).show()

                findNavController().navigate(R.id.signInFragment)

            }
        }


        return binding.root
    }
}