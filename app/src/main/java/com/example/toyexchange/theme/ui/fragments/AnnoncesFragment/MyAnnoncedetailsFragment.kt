package com.example.toyexchange.theme.ui.fragments.AnnoncesFragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.toyexchange.Presentation.ToysViewModel.AddAuctionViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.MyAnnonceDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyAnnoncedetailsFragment: Fragment(R.layout.my_annonce_details) {
    private lateinit var addAuctionViewModel:AddAuctionViewModel



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = MyAnnonceDetailsBinding.inflate(inflater, container, false)

        addAuctionViewModel = ViewModelProvider(this).get(AddAuctionViewModel::class.java)

        val sharedPreferences =
            requireActivity().getSharedPreferences("authToken", Context.MODE_PRIVATE)
        val token =sharedPreferences.getString("authToken",null)
        //addToyViewModel.token=token
        val idAnnonce = arguments?.getLong("id")
        Log.i("id annonce",idAnnonce.toString())


        // get item selected
        val name = arguments?.getString("name").toString()
        val description = arguments?.getString("description").toString()
        val _price = arguments?.getString("price").toString()
        val _category = arguments?.getString("category").toString()
        val age_child = arguments?.getString("age_child").toString()
        val age_toy = arguments?.getString("age_toy").toString()
        val _state = arguments?.getString("state").toString()
        val _image= arguments?.getString("image").toString()

        binding.apply {
            toyName.setText(name)
            toyDescription.setText(description)
            childAge.setText(age_child)
            category.setText(_category)
            state.setText(_state)
            toyAge.setText(age_toy)
        }
        val bundle = bundleOf("id" to idAnnonce,
            "name" to name, "description" to description,
            "price" to _price,"category" to _category,
            "age_child" to age_child,"age_toy" to age_toy,
            "state" to _state)

        binding.Modifiy.setOnClickListener {
            findNavController().navigate(R.id.action_myAnnoncedetailsFragment_to_modifyAnnonceFragment,bundle)
        }
        binding.addToAuction.setOnClickListener{
            findNavController().navigate(R.id.action_myAnnoncedetailsFragment_to_addAuctionFragment,bundle)

        }
        addAuctionViewModel.checkExistentAuction(idAnnonce!!,token.toString())
        addAuctionViewModel.msg.observe(viewLifecycleOwner, Observer {
            if (it==null){
                binding.addToAuction.visibility=View.VISIBLE
            }
        })


        return binding.root

    }
}