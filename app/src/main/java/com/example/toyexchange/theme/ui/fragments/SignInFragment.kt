package com.example.toyexchange.theme.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.toyexchange.Domain.model.Request
import com.example.toyexchange.Domain.model.UserLoginResponse
import com.example.toyexchange.Presentation.ToysViewModel.LoginViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.SignInFragmentBinding

class SignInFragment: Fragment(R.layout.sign_in_fragment) {

    private lateinit var loginViewModel: LoginViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = SignInFragmentBinding.inflate(inflater, container, false)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        Log.i("1","1")
        binding.signInButton.setOnClickListener {
            val email=binding.signInEmail.text.toString()
            Log.i("email",email)
            val password=binding.signInPassword.text.toString()
            Log.i("password",password)
            val request= Request(email,password)
        loginViewModel.userLogin(request)
            //Toast.makeText(requireContext(),"button clicked",Toast.LENGTH_LONG).show()
        }
        loginViewModel.token.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                Toast.makeText(requireContext(),"the user ${it.username} is added",Toast.LENGTH_LONG).show()
                Log.i("token",it.auth_token)
        }
            else{
                Toast.makeText(requireContext(),"the user ${it?.username} is failed to be added",Toast.LENGTH_LONG).show()

            }
        })
        /* loginViewModel.token.observe(viewLifecycleOwner, { token ->
             Log.i("token",token.auth_token)
         }) */
            //findNavController().navigate(R.id.action_signInFragment_to_feedToysFragment)

        return binding.root

    }
}

