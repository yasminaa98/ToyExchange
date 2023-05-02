package com.example.toyexchange.theme.ui.fragments.AuthenticationFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.toyexchange.Domain.model.User
import com.example.toyexchange.Presentation.ToysViewModel.SignUpViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.AddPasswordFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPasswordFragment : Fragment(R.layout.add_password_fragment) {
    private lateinit var signUpViewModel:SignUpViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = AddPasswordFragmentBinding.inflate(inflater, container, false)
        signUpViewModel=ViewModelProvider(this).get(SignUpViewModel::class.java)
        val firstname = arguments?.getString("firstname").toString()
        val lastname = arguments?.getString("lastname").toString()
        val email = arguments?.getString("email").toString()
        val username = arguments?.getString("username").toString()
        val address = arguments?.getString("address").toString()
        val avg_response = arguments?.getString("avg_response").toString()
        val phone = arguments?.getInt("phone")!!
        binding.signupButton.setOnClickListener {
            val password = binding.password.text.toString()
            val repeatPassword = binding.repeatPassword.text.toString()
            if (password == repeatPassword) {
                val user = User(
                    address,
                    email,
                    1,
                    firstname,
                    lastname,
                    username,
                    password,
                    phone,
                    avg_response
                )
                Log.i("info", user.apply {
                    firstname
                    lastname
                    email
                    username
                    address
                    avg_response
                    phone
                    password
                }.toString())
                signUpViewModel.userSignUp(user)

            }
            else{
                Toast.makeText(requireContext(),"password not compatible",Toast.LENGTH_LONG).show()

            }


        }
        signUpViewModel.msg.observe(viewLifecycleOwner , Observer {
            if(it!=null){
                Toast.makeText(requireContext(),"sign up is successful",Toast.LENGTH_LONG).show()
                Log.i("msg",it)
            }
            else{
                Toast.makeText(requireContext(),"sign up failed",Toast.LENGTH_LONG).show()

            }
        })
        return binding.root
    }
}