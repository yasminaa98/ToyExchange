package com.example.toyexchange.theme.ui.fragments.AuctionFragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.toyexchange.Domain.model.AuctionResponse
import com.example.toyexchange.Presentation.ToysViewModel.AddAuctionViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.AddAuctionFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddAuctionFragment: Fragment(R.layout.add_auction_fragment){
    private lateinit var addAuctionViewModel: AddAuctionViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = AddAuctionFragmentBinding.inflate(inflater, container, false)
        addAuctionViewModel= ViewModelProvider(this).get(AddAuctionViewModel::class.java)
        val sharedPreferences =
            requireActivity().getSharedPreferences("authToken", Context.MODE_PRIVATE)
        val token =sharedPreferences.getString("authToken",null)
        val idAnnonce = arguments?.getLong("id")

        //addToyViewModel.token=token
        binding.addAuction.setOnClickListener {
            val name=binding.auctionName.text.toString()
            val endDate=binding.endDate.text.toString()
            val start=binding.startDate.text.toString()
            val price=binding.initialPrice.text.toString()
            val description=binding.description.text.toString()
            val auction= AuctionResponse(1,name,price,description,"",start,endDate)
            addAuctionViewModel.addAuction(idAnnonce!!,auction,token.toString())
        }
        addAuctionViewModel.msg.observe(viewLifecycleOwner , Observer {
            if(it!=null){
                Toast.makeText(requireContext(),"auction added successfully", Toast.LENGTH_LONG).show()

                findNavController().navigate(R.id.action_addAuctionFragment_to_myAuctionFragment)
            }
            else{
                Toast.makeText(requireContext(),"auction adding failed", Toast.LENGTH_LONG).show()
            }
        })
        return binding.root

    }
}