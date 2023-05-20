package com.example.toyexchange.theme.ui.fragments.AuctionFragments


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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.toyexchange.Common.CountDownManager
import com.example.toyexchange.Common.PicturesConverter
import com.example.toyexchange.Presentation.ToysViewModel.AuctionDetailsViewModel
import com.example.toyexchange.Presentation.ToysViewModel.DetailsToyViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.MyAuctionDetailsBinding
import com.example.toyexchange.theme.ui.MainActivity
import com.example.toyexchange.theme.ui.adapter.BidsAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@Suppress("UNREACHABLE_CODE")
@AndroidEntryPoint
class MyAuctionDetailsFragment: Fragment(R.layout.my_auction_details) {
    private lateinit var auctionDetailsViewModel: AuctionDetailsViewModel
    private lateinit var detailsToyViewModel: DetailsToyViewModel
    lateinit var bidsAdapter: BidsAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = MyAuctionDetailsBinding.inflate(inflater, container, false)

        auctionDetailsViewModel = ViewModelProvider(this).get(AuctionDetailsViewModel::class.java)
        detailsToyViewModel = ViewModelProvider(this).get(DetailsToyViewModel::class.java)

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
        CountDownManager.startCountDown(endDateTime.toString(), binding.endDateTime, {
            binding.endDateTime.text = "Closed"
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
                Toast.makeText(
                    requireContext(),
                    "getting info failed",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        })
        auctionDetailsViewModel.getAuctionOwner(auctionId!!)
        auctionDetailsViewModel.auctionOwner.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                binding.apply {
                    Glide.with(requireActivity())
                        .load("http://192.168.100.47:2023/image/fileSystem/"+it.profile_picture_path)
                        //.apply(RequestOptions.circleCropTransform()) // Apply circular crop transformation
                        .into(binding.ownerImage)

                }
            }
        })
        detailsToyViewModel.getAnnonceByAuction(auctionId!!)
        detailsToyViewModel.annonce.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                Glide.with(requireActivity())
                    .load("http://192.168.100.47:2023/image/fileSystem/"+it.picturePath)
                    //.apply(RequestOptions.circleCropTransform())
                    .into(binding.toyImage)
                    Toast.makeText(requireContext(), "picture got successfully", Toast.LENGTH_LONG)
                        .show()
                }else {
                Toast.makeText(requireContext(), "getting picture failed", Toast.LENGTH_LONG).show()
            }
        })
        // get bids list
                binding.bidsList.apply {
                    layoutManager = LinearLayoutManager(this.context)
                    auctionDetailsViewModel.bids.observe(viewLifecycleOwner) { priceProposed ->
                        bidsAdapter = BidsAdapter(priceProposed,
                            BidsAdapter.OnClickListener { clickedItem ->
                                val database = FirebaseDatabase.getInstance()
                                val userRef = database.getReference("user")
                                val nameToFind = clickedItem.username

                                userRef.orderByChild("name").equalTo(nameToFind).addListenerForSingleValueEvent(object :
                                    ValueEventListener {
                                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                                        for (userSnapshot in dataSnapshot.children) {
                                            val uid = userSnapshot.child("uid").getValue(String::class.java)
                                            Log.i("uid clicked is ", uid.toString())

                                            val bundle= bundleOf("uid" to uid)
                                            findNavController().navigate(R.id.action_myAuctionDetailsFragment_to_chat,bundle)
                                        }
                                    }
                                    override fun onCancelled(databaseError: DatabaseError) {
                                        Log.i("uid clicked is ", "error")

                                        // Handle error
                                    }
                                })
                            }, lifecycleScope
                        )
                        binding.bidsList.adapter = bidsAdapter

                    }
                    auctionDetailsViewModel.getAuctionBids(auctionId!!, token.toString())
                    Log.i("bids fetched", "bids fetched")
                }
        //delete auction when clicking
        binding.delete.setOnClickListener{
            auctionDetailsViewModel.deleteAuction(auctionId!!)
            findNavController().navigate(
                R.id.action_myAuctionDetailsFragment_to_myAuctionFragment)
        }

        return binding.root

            }


    }