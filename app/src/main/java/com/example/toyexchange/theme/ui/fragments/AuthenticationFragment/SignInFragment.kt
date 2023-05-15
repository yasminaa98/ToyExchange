package com.example.toyexchange.theme.ui.fragments.AuthenticationFragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.toyexchange.Domain.model.Request
import com.example.toyexchange.Presentation.ToysViewModel.LoginViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.SignInFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment: Fragment(R.layout.sign_in_fragment) {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = SignInFragmentBinding.inflate(inflater, container, false)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        mAuth = FirebaseAuth.getInstance()

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
                Toast.makeText(requireContext(),"the user ${it.username} is logged in ",Toast.LENGTH_LONG).show()
                login(it.email,binding.signInPassword.text.toString())
                val auth = FirebaseAuth.getInstance()
                val currentUser = auth.currentUser
                val uid = currentUser?.uid
                val sharedPreferences =
                requireActivity().getSharedPreferences("authToken", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("authToken", it.auth_token)
                editor.putString("username",it.username)
                editor.putLong("idUser", it.id)
                editor.putString("email", it.email)
                editor.putString("uid", uid)
                editor.apply()
                findNavController().navigate(R.id.action_signInFragment_to_feedToysFragment)
        }
            else{
                Toast.makeText(requireContext(),"the user is failed to login",Toast.LENGTH_LONG).show()

            }

        })
      binding.register.setOnClickListener{
          findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
      }
        binding.forgotPassword.setOnClickListener{
            findNavController().navigate(R.id.action_signInFragment_to_forgotPasswordFragment)

        }



        return binding.root

    }
    private fun login(email: String, password: String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireActivity(), "User logged in", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(requireActivity(), "User does not exist", Toast.LENGTH_SHORT).show()
                }
            }
    }

}

