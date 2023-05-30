package com.example.toyexchange.theme.ui.fragments.AuctionFragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
import java.text.SimpleDateFormat
import java.util.*

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
        val associations = arrayOf("SOS Gammart", "1 Enfant 1 Espoire", "Sgharna", "El Amal association")
        val adapter = ArrayAdapter<String>(requireContext(),R.layout.spinner_item_layout, associations)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.description.adapter = adapter

        binding.description.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedLanguage = parent.getItemAtPosition(position).toString()
                Toast.makeText(requireContext(), "Selected language: $selectedLanguage", Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Ne rien faire ici
            }
        }
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val start = dateFormat.format(Date())
        binding.startDate.setText(start)
        //addToyViewModel.token=token
        binding.addAuction.setOnClickListener {
            val name = binding.auctionName.text.toString()
            val price = binding.initialPrice.text.toString()
            val description = binding.description.selectedItem.toString()
            val endDate = binding.endDate.text.toString()
            if (name.isNotEmpty() && price.isNotEmpty() && endDate.isNotEmpty()) {
                val regexPattern = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}".toRegex()
                val isValidFormat = regexPattern.matches(endDate)
                if (isValidFormat) {
                    val enteredDateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(endDate)
                    val currentDateTime = Date()
                    if (enteredDateTime != null && enteredDateTime.after(currentDateTime)) {
                        binding.endDate.error = null
                        val auction = AuctionResponse(1, name, price, description, "", start, endDate)
                        addAuctionViewModel.addAuction(idAnnonce!!, auction, token.toString())
                    } else {
                        binding.endDate.error = "Entered date and time must be after the current date and time"
                    }
                } else {
                    binding.endDate.error = "Invalid format. Expected: yyyy-MM-dd HH:mm:ss"
                }
            } else {
                if (name.isEmpty()) {
                    binding.auctionName.error = "Name field is required"
                }
                if (price.isEmpty()) {
                    binding.initialPrice.error = "Price field is required"
                }
                if (endDate.isEmpty()) {
                    binding.endDate.error = "End Date field is required"
                }
            }
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