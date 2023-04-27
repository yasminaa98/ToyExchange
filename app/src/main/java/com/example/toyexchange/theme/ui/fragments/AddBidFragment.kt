package com.example.toyexchange.theme.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.toyexchange.Common.CountDownManager
import com.example.toyexchange.Domain.model.Annonce
import com.example.toyexchange.Domain.model.Bid
import com.example.toyexchange.Presentation.ToysViewModel.AddBidViewModel
import com.example.toyexchange.Presentation.ToysViewModel.AddToyViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.AddToyFragmentBinding
import com.example.toyexchange.databinding.PlaceBidBinding
import com.example.toyexchange.theme.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddBidFragment:Fragment(R.layout.place_bid) {
    private lateinit var addBidViewModel: AddBidViewModel

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = PlaceBidBinding.inflate(inflater, container, false)
        addBidViewModel = ViewModelProvider(this).get(AddBidViewModel::class.java)

        (activity as MainActivity).setBottomNavigation(false)
        (activity as MainActivity).setToolbar(true)
        val sharedPreferences =
            requireActivity().getSharedPreferences("authToken", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("authToken", null)
        val username = sharedPreferences.getString("username", null)
        val auctionId = arguments?.getLong("idAuction")
        val endDateTime=arguments?.getString("end_dateTime")


        //add timer
        binding.timer.text=endDateTime

        CountDownManager.startCountDown(endDateTime.toString(),binding.timer,{

               findNavController().navigate(
                    R.id.action_addBidFragment_to_auctionDetailsFragment
                )

        })
        addBidViewModel.getUserBid(auctionId!!, token.toString())
        addBidViewModel.bid.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.myPrice.setText(it.price_proposed)
                binding.note.setText(it.note)
                Toast.makeText(requireContext(), "bid user", Toast.LENGTH_LONG).show()
                Log.i("msg", it.toString())
            } else {
                Toast.makeText(requireContext(), "bid getting failed", Toast.LENGTH_LONG).show()
            }
        })

        binding.done.setOnClickListener {
            val my_price = addBidViewModel.bid.value?.price_proposed
            val note = addBidViewModel.bid.value?.note
            Log.i("price and note", my_price + note)

            if (my_price!=null && my_price!= binding.myPrice.text.toString()) {
                Log.i("not the same", my_price + note)
                val newPrice = binding.myPrice.text.toString()
                if (newPrice.toInt()> my_price.toInt()) {
                    addBidViewModel.updateBidPrice(auctionId, newPrice, token.toString())
                    addBidViewModel.priceUpdated.observe(viewLifecycleOwner, Observer {
                        if (it != null) {
                            Toast.makeText(requireContext(), "price updated", Toast.LENGTH_LONG)
                                .show()
                            Log.i("msg", it.toString())
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "price updating  failed",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    })
                    findNavController().navigate(
                        R.id.action_addBidFragment_to_auctionFragment
                    )
                }
                else{
                    Toast.makeText(requireContext(), "if you decided to update the price to bigger i guess ! ", Toast.LENGTH_LONG)
                        .show()

                }
            } else if(my_price == null && note==null) {
                val priceToAdd= binding.myPrice.text.toString()
                val noteToAdd= binding.note.text.toString()
                val bid = Bid(30, noteToAdd, priceToAdd, username.toString(), auctionId!!)
                Log.i("bid added is ", my_price + note)
                addBidViewModel.addBid(bid, auctionId, token.toString())
                addBidViewModel.response.observe(viewLifecycleOwner, Observer {
                    if (it != null) {
                        Toast.makeText(requireContext(), "bid added", Toast.LENGTH_LONG).show()
                        Log.i("msg", it.toString())
                    } else {
                        Toast.makeText(requireContext(), "bid adding failed", Toast.LENGTH_LONG)
                            .show()
                    }
                })
                findNavController().navigate(
                    R.id.action_addBidFragment_to_auctionFragment
                )

            }


        }








        return binding.root

    }

    fun addBid() {
    }

    fun updateBid() {

    }
}