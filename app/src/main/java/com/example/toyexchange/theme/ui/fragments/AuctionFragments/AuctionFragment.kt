package com.example.toyexchange.theme.ui.fragments.AuctionFragments

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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.toyexchange.Presentation.ToysViewModel.AuctionViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.AuctionsFragmentBinding
import com.example.toyexchange.theme.ui.MainActivity
import com.example.toyexchange.theme.ui.adapter.AuctionAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuctionFragment: Fragment(R.layout.auctions_fragment) {


    private lateinit var auctionViewModel: AuctionViewModel
    lateinit var auctionAdapter: AuctionAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = AuctionsFragmentBinding.inflate(inflater, container, false)
        // create instance of viewmodel , the life cycle library creates it for us so if the viewmodel destroyed we don't need to recreated
        auctionViewModel = ViewModelProvider(this).get(AuctionViewModel::class.java)
        (activity as MainActivity).setBottomNavigation(true)
        (activity as MainActivity).setToolbar(false)
        //coundown
       binding.auctionsList.apply {
            layoutManager = GridLayoutManager(this.context, 2)

            //when ever the data changes this code below is called
            //it's the observer of the live data
            auctionViewModel.auctions.observe(viewLifecycleOwner) { auctions ->

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
                        findNavController().navigate(
                            R.id.action_auctionFragment_to_auctionDetailsFragment,
                            bundle
                        )
                    })
                binding.auctionsList.adapter = auctionAdapter
            }
            auctionViewModel.getAllAuctions()
            Log.i("toys fetched", "toys fetched")

        }
        binding.addAuction.setOnClickListener{
            findNavController().navigate(
                R.id.action_auctionFragment_to_addAuctionFragment
            )

        }
        return binding.root
    }
}