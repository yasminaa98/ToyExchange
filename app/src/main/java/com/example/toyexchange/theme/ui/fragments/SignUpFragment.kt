package com.example.toyexchange.theme.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.toyexchange.Presentation.ToysViewModel.LoginViewModel
import com.example.toyexchange.Presentation.ToysViewModel.SignUpViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.SignUpFragmentBinding
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment:Fragment(R.layout.sign_up_fragment) {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = SignUpFragmentBinding.inflate(inflater, container, false)

        binding.nextButton.setOnClickListener {
            val firstname=binding.firstname.text.toString()
            val lastname=binding.lastname.text.toString()
            val email=binding.email.text.toString()
            val username=binding.username.text.toString()
            val address=binding.address.text.toString()
            val avg_response=binding.avgResponse.text.toString()
            val phone=binding.phone.text
            val bundle = bundleOf("firstname" to firstname,
                "lastname" to lastname, "email" to email,
                "username" to username,"address" to address,
                "avg_response" to avg_response, "phone" to phone)
            Log.i("info",firstname)
            Log.i("info",lastname)
            Log.i("info",email)
            Log.i("info",username)
            Log.i("info",address)
            Log.i("info",avg_response)
            Log.i("info",phone.toString())
            findNavController().navigate(R.id.action_signUpFragment_to_addPasswordFragment,bundle)
        }
        return binding.root

    }
}