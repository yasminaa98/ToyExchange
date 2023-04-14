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
import com.example.toyexchange.Domain.model.Annonce
import com.example.toyexchange.Presentation.ToysViewModel.AddToyViewModel
import com.example.toyexchange.Presentation.ToysViewModel.ModifyAnnonceViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.AddToyFragmentBinding

class ModifyAnnonceFragment: Fragment(R.layout.modify_annonce_fragment) {
    private lateinit var modifyAnnonceViewModel: ModifyAnnonceViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = AddToyFragmentBinding.inflate(inflater, container, false)
        modifyAnnonceViewModel= ViewModelProvider(this).get(ModifyAnnonceViewModel::class.java)
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
            val annonce= Annonce(category,description,name,price,state,child_age,toy_age)
            modifyAnnonceViewModel.modifyAnnonce(token.toString(), idAnnonce = "1",annonce)
        }
        modifyAnnonceViewModel.msg.observe(viewLifecycleOwner , Observer {
            if(it!=null){
                Toast.makeText(requireContext(),"toy modified successfully", Toast.LENGTH_LONG).show()
                Log.i("msg",it.toString())
            }
            else{
                Toast.makeText(requireContext(),"toy modification failed", Toast.LENGTH_LONG).show()
            }
        })
        return binding.root

    }

}