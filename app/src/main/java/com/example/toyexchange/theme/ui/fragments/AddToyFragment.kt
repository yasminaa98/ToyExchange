package com.example.toyexchange.theme.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.toyexchange.Domain.model.Annonce
import com.example.toyexchange.Domain.model.Toy
import com.example.toyexchange.Presentation.ToysViewModel.AddToyViewModel
import com.example.toyexchange.Presentation.ToysViewModel.DetailsToyViewModel
import com.example.toyexchange.Presentation.ToysViewModel.SignUpViewModel
import com.example.toyexchange.R
import com.example.toyexchange.data.remote.RetrofitClient
import com.example.toyexchange.databinding.AddToyFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddToyFragment: Fragment(R.layout.add_toy_fragment) {
    private lateinit var addToyViewModel: AddToyViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = AddToyFragmentBinding.inflate(inflater, container, false)
        addToyViewModel= ViewModelProvider(this).get(AddToyViewModel::class.java)
        val sharedPreferences =
            requireActivity().getSharedPreferences("authToken", Context.MODE_PRIVATE)
        val token =sharedPreferences.getString("authToken",null)
        //addToyViewModel.token=token
        binding.addButton.setOnClickListener {
            val name=binding.toyName.text.toString()
            val child_age=binding.childAge.text.toString()
            val toy_age=binding.toyAge.text.toString()
            val price=binding.price.text.toString()
            val category=binding.toyCategory.text.toString()
            val state=binding.toyState.text.toString()
            val description=binding.toyDescription.text.toString()
            val annonce= Annonce(1,category,description,"",name,price,state,child_age,toy_age)
            addToyViewModel.addToy(token.toString(),annonce)
        }
        addToyViewModel.adding_msg.observe(viewLifecycleOwner , Observer {
            if(it!=null){
                Toast.makeText(requireContext(),"toy added successfully", Toast.LENGTH_LONG).show()
                Log.i("msg",it.toString())
            }
            else{
                Toast.makeText(requireContext(),"toy adding failed", Toast.LENGTH_LONG).show()
            }
        })
        return binding.root

    }
}