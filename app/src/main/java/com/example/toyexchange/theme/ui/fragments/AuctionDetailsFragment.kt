package com.example.toyexchange.theme.ui.fragments

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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.toyexchange.Common.CountDownManager
import com.example.toyexchange.Presentation.ToysViewModel.AuctionDetailsViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.AuctionDetailsBinding
import com.example.toyexchange.theme.ui.MainActivity
import com.example.toyexchange.theme.ui.adapter.BidsAdapter
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AuctionDetailsFragment:Fragment(R.layout.auction_details) {
    private lateinit var auctionDetailsViewModel: AuctionDetailsViewModel
    lateinit var bidsAdapter:BidsAdapter
    private val countdownId = UUID.randomUUID().toString()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = AuctionDetailsBinding.inflate(inflater, container, false)

        auctionDetailsViewModel = ViewModelProvider(this).get(AuctionDetailsViewModel::class.java)
        (activity as MainActivity).setBottomNavigation(false)
        (activity as MainActivity).setToolbar(true)
        val sharedPreferences =
            requireActivity().getSharedPreferences("authToken", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("authToken", null)
        Log.i("token1", token.toString())

        val auctionId = arguments?.getLong("id_auction")
        val name = arguments?.getString("name")
        val description = arguments?.getString("description")
        val endDateTime = arguments?.getString("end_dateTime")
        val startDateTime = arguments?.getString("start_datetime")
        val initial_price = arguments?.getString("initial_price")
        val bundle = bundleOf(
            "idAuction" to auctionId,
            "end_dateTime" to endDateTime
        )
        Log.i("bundle", bundle.toString())
        Log.i("auctionId", auctionId.toString())

        binding.toyName.text = name
        binding.toyDescription.text = description
        binding.endDateTime.text = endDateTime
        Log.i("endDateTime", endDateTime.toString())


        /* val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val currentDateTime = dateFormat.format(calendar.time)
        Log.i("currentDateTime", currentDateTime.toString())*/

        val endDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val futureDateTime: Date? = endDateTime?.let { endDateFormat.parse(it) }
        if (futureDateTime != null) {
            val calendar = Calendar.getInstance()
            calendar.time = futureDateTime
            val now = Calendar.getInstance()

            if (calendar.before(now)) {
                Log.i("before", "before")
                binding.place.isEnabled = true
                binding.place.text = "you can't place a bid !"
                binding.endDateTime.text = "Time is over !"
            } else if (calendar.after(now)) {
                Log.i("after", "after")

                CountDownManager.startCountDown(endDateTime.toString(), binding.endDateTime,{
                    if(countdownId==this.countdownId){
                    findNavController().navigate(
                        R.id.action_auctionDetailsFragment_to_auctionFragment
                    ) }
                })

                auctionDetailsViewModel.getAuctionPrice(auctionId!!, token.toString())
                auctionDetailsViewModel.price.observe(viewLifecycleOwner, Observer {
                    if (it != null) {
                        //auctionDetailsViewModel.getAuctionPrice(auctionId!!,token.toString())
                        val jsonObject = Gson().fromJson(it, JsonObject::class.java);
                        val messageValue = jsonObject.get("message").asString
                        binding.toyPrice.text = messageValue.toString()
                        Toast.makeText(requireContext(), "last price", Toast.LENGTH_LONG).show()
                        Log.i("last price", it.toString())
                    } else {
                        Toast.makeText(requireContext(), "getting info failed", Toast.LENGTH_LONG)
                            .show()
                    }
                })

                binding.place.setOnClickListener {
                    Log.i("clicked", "clicked")
                    findNavController().navigate(
                        R.id.action_auctionDetailsFragment_to_addBidFragment, bundle
                    )
                }


                // get bids list

                binding.bidsList.apply {
                    layoutManager = LinearLayoutManager(this.context)
                    auctionDetailsViewModel.bids.observe(viewLifecycleOwner) { priceProposed ->
                        bidsAdapter = BidsAdapter(priceProposed)
                        binding.bidsList.adapter = bidsAdapter

                    }
                    auctionDetailsViewModel.getAuctionBids(auctionId!!, token.toString())
                    Log.i("bids fetched", "bids fetched")
                    return binding.root
                }


            }
        }
            //countdown

            //binding.toyPrice.text=initial_price


            return binding.root

        }
    }