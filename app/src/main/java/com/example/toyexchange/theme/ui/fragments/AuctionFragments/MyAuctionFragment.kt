package com.example.toyexchange.theme.ui.fragments.AuctionFragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.toyexchange.Presentation.ToysViewModel.MyAuctionsViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.MyAuctionsFragmentBinding
import com.example.toyexchange.theme.ui.MainActivity
import com.example.toyexchange.theme.ui.adapter.AuctionAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyAuctionFragment :Fragment(R.layout.my_auctions_fragment){
    private lateinit var myAuctionsViewModel: MyAuctionsViewModel
    lateinit var auctionAdapter: AuctionAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = MyAuctionsFragmentBinding.inflate(inflater, container, false)
        // create instance of viewmodel , the life cycle library creates it for us so if the viewmodel destroyed we don't need to recreated
        myAuctionsViewModel = ViewModelProvider(this).get(MyAuctionsViewModel::class.java)
        (activity as MainActivity).setBottomNavigation(true)
        (activity as MainActivity).setToolbar(false)
        /* toysRecyclerViewAdapter = ToysRecyclerViewAdapter(emptyList(),ToysRecyclerViewAdapter.OnClickListener{ photo ->
            Toast.makeText(context, "${photo.name}", Toast.LENGTH_SHORT).show() }) */
        //get the stored token
        val sharedPreferences =
            requireActivity().getSharedPreferences("authToken", Context.MODE_PRIVATE)
        val token =sharedPreferences.getString("authToken",null)

        binding.myauctionsList.apply {
            layoutManager = LinearLayoutManager(this.context)

            //when ever the data changes this code below is called
            //it's the observer of the live data
            myAuctionsViewModel.auctions.observe(viewLifecycleOwner, { auctions ->
                auctionAdapter = AuctionAdapter(auctions,
                    AuctionAdapter.OnClickListener { clickedItem ->
                        val bundle = bundleOf(
                            "id_auction" to clickedItem.id,
                            "name" to clickedItem.name,
                            "description" to clickedItem.description,
                            "initial_price" to clickedItem.initial_price,
                            "end_dateTime" to clickedItem.end_dateTime,
                            "start_datetime" to clickedItem.start_datetime
                        )
                        Log.i("the bundle", bundle.toString())
                        findNavController().navigate(
                            R.id.action_myAuctionFragment_to_myAuctionDetailsFragment,
                            bundle
                        )

                    })
                binding.myauctionsList.adapter = auctionAdapter
            })
            myAuctionsViewModel.getUserAuctions(token.toString())
            Log.i("auctions fetched", "auctions fetched")

            binding.addAuction.setOnClickListener{
                findNavController().navigate(
                    R.id.action_myAuctionFragment_to_myAnnoncesFragment
                )

            }

            //
        }
            return binding.root
        }



}

