package com.example.toyexchange.theme.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.toyexchange.Presentation.ToysViewModel.ForgotPasswordViewModel
import com.example.toyexchange.Presentation.ToysViewModel.LoginViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.ForgotPasswordFragmentBinding
import com.example.toyexchange.databinding.SignInFragmentBinding
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment: Fragment(R.layout.forgot_password_fragment) {

    private lateinit var forgotPasswordViewModel: ForgotPasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = ForgotPasswordFragmentBinding.inflate(inflater, container, false)
        forgotPasswordViewModel = ViewModelProvider(this).get(ForgotPasswordViewModel::class.java)
        binding.send.setOnClickListener {
            val email = binding.email.text.toString()
            Log.i("email", email)
            forgotPasswordViewModel.forgotPassword(email)
        }
            forgotPasswordViewModel.tokenMsg.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    Toast.makeText(
                        requireContext(),
                        "An email with instructions to reset your password has been sent to your email address ",
                        Toast.LENGTH_LONG
                    ).show()
                    val sharedPreferences =
                        requireActivity().getSharedPreferences("tokenMessage", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("tokenMessage", it.tokenMessage.toString())
                    Log.i("token msg", it.tokenMessage.toString())
                    editor.apply()
                    Log.i("token msg saved", editor.toString())


                } else {
                    Toast.makeText(requireContext(), "User not found", Toast.LENGTH_LONG).show()


                }
            })

        return binding.root
    }
}