package com.example.toyexchange.theme.ui.fragments.AuthenticationFragment

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
import androidx.navigation.fragment.findNavController
import com.example.toyexchange.Domain.model.User
import com.example.toyexchange.theme.ui.fragments.ChatFragment.UserFirebase
import com.example.toyexchange.Presentation.ToysViewModel.SignUpViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.AddPasswordFragmentBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPasswordFragment : Fragment(R.layout.add_password_fragment) {
    private lateinit var signUpViewModel:SignUpViewModel
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference
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
        mAuth = FirebaseAuth.getInstance()
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
                    avg_response,"no picture yet"
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
                FirebaseApp.initializeApp(requireContext());
                signUp(username,email,password)

            }
            else{
                Toast.makeText(requireContext(),"password not compatible",Toast.LENGTH_LONG).show()

            }
        }
        signUpViewModel.msg.observe(viewLifecycleOwner , Observer {
            if(it!=null){
                Toast.makeText(requireContext(),"sign up is successful",Toast.LENGTH_LONG).show()
                Log.i("msg",it.message)
                val sharedPreferences =
                    requireActivity().getSharedPreferences("authToken", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("authToken", it.auth_token)
                editor.apply()
            }
            else{
                Toast.makeText(requireContext(),"sign up failed: username or email are already taken",Toast.LENGTH_LONG).show()
                findNavController().navigate(
                    R.id.action_addPasswordFragment_to_signUpFragment)
            }
        })
        return binding.root
    }
    private fun signUp(username: String, email: String, password: String){
        //logic of creating user
        //https://firebase.google.com/docs/auth/android/password-auth
        //get information from firebase docs
        Log.d("ITM",email)
        Log.d("ITM",password)
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d("ITM","success signup")
                    // code for jumping to home

                    // add user To Database
                    addUserToDatabase(username, email, mAuth.currentUser?.uid!!) // none safe!
                } else {
                    Log.d("ITM","fail signup")
                    Toast.makeText(requireActivity(), "Some error occurred", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // add user To Database
    private fun addUserToDatabase(name:String, email:String, uid:String){
        // use real
        Log.d("ITM","function start")
        mDbRef = Firebase.database.reference
        Log.i("ref",mDbRef.toString())
        Log.d("ITM",uid)
        Log.d("ITM",name)
        Log.d("ITM",email)
        mDbRef.child("user").child(name).setValue(UserFirebase(name, email, uid)) // add user to database
    }

}